package net.orekyuu.moco.core;

import net.orekyuu.moco.core.attribute.Predicate;
import net.orekyuu.moco.core.relation.Preloader;
import net.orekyuu.moco.core.relation.Relation;
import net.orekyuu.moco.feeling.Delete;
import net.orekyuu.moco.feeling.Select;
import net.orekyuu.moco.feeling.Table;
import net.orekyuu.moco.feeling.Update;
import net.orekyuu.moco.feeling.node.*;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public abstract class EntityList<T extends EntityList<T, E>, E> {

    protected final Table table;
    protected Optional<WhereClause> whereClause = Optional.empty();
    protected Optional<SqlLimit> sqlLimit = Optional.empty();
    protected Optional<SqlOffset> sqlOffset = Optional.empty();
    private final List<Relation<E>> preloadRelations = new ArrayList<>();
    private final List<SqlOrderigTerm> orders = new ArrayList<>();

    public EntityList(Table table) {
        this.table = table;
    }

    protected Select createSelect() {
        Select select = table.select();
        whereClause.ifPresent(select::where);
        sqlLimit.ifPresent(select::limit);
        sqlOffset.ifPresent(select::offset);
        select.order(orders);
        return select;
    }

    protected Delete createDelete() {
        Delete delete = table.delete();
        whereClause.ifPresent(delete::where);
        sqlLimit.ifPresent(delete::limit);
        if (sqlOffset.isPresent()) {
            throw new UnsupportedOperationException("offset not supported.");
        }
        delete.order(orders);
        return delete;
    }

    @SafeVarargs
    protected final Update createUpdate(UpdateValuePair<E, ?>... pairs) {
        Update update = table.update();
        whereClause.ifPresent(update::where);
        for (UpdateValuePair<E, ?> pair : pairs) {
            update.addSetValue(pair.getAttribute().ast(), pair.getSqlNode());
        }
        return update;
    }

    public T where(Predicate... predicates) {
        Arrays.stream(predicates).forEach(this::where);
        return (T) this;
    }

    public T where(Predicate predicate) {
        WhereClause clause = this.whereClause.map(where -> {
            SqlNodeExpression expression = where.getExpression().and(predicate.getExpression());
            where.setExpression(expression);
            return where;
        }).orElseGet(() -> new WhereClause(predicate.getExpression()));
        this.whereClause = Optional.of(clause);
        return (T)this;
    }

    public T order(SqlOrderigTerm term) {
        orders.add(term);
        return (T)this;
    }

    public T order(SqlOrderigTerm ... term) {
        orders.addAll(Arrays.asList(term));
        return (T)this;
    }

    public T limit(int limit) {
        sqlLimit = Optional.of(new SqlLimit(new SqlBindParam<>(limit, Integer.class)));
        return (T)this;
    }

    public T limitAndOffset(int limit, int offset) {
        limit(limit);
        sqlOffset = Optional.of(new SqlOffset(new SqlBindParam<>(limit, Integer.class)));
        return (T)this;
    }

    public T preload(Relation<E> relation) {
        preloadRelations.add(relation);
        return (T)this;
    }

    @SafeVarargs
    public final T preload(Relation<E>... relation) {
        preloadRelations.addAll(Arrays.asList(relation));
        return (T)this;
    }

    public void delete() {
        createDelete().executeQuery(ConnectionManager.getConnection(), ConnectionManager.createSqlVisitor());
    }

    @SafeVarargs
    public final void update(UpdateValuePair<E, ?>... pairs) {
        createUpdate(pairs).executeQuery(ConnectionManager.getConnection(), ConnectionManager.createSqlVisitor());
    }

    public List<E> toList() {
        List<E> records = createSelect().executeQuery(ConnectionManager.getConnection(), ConnectionManager.createSqlVisitor(), getMapper());
        Preloader<E> preloader = new Preloader<>();
        preloader.preload(records, preloadRelations);
        return records;
    }

    @Nonnull
    public Optional<E> first() {
        limit(1);
        return toList().stream().findFirst();
    }

    @Nullable
    public E firstOrNull() {
        return first().orElse(null);
    }

    public Stream<E> stream() {
        return stream(1000);
    }

    public Stream<E> stream(int batchSize) {
        Iterator<E> iterator = new Iterator<E>() {
            int currentPage = 0;
            private Iterator<E> list;
            @Override
            public boolean hasNext() {
                if (list == null) {
                    list = executeQuery().iterator();
                    return list.hasNext();
                }
                if (list.hasNext()) {
                    return true;
                }
                list = executeQuery().iterator();
                return list.hasNext();
            }

            private List<E> executeQuery() {
                List<E> records = createSelect()
                        .limit(new SqlLimit(new SqlLiteral(String.valueOf(batchSize))))
                        .offset(new SqlOffset(new SqlLiteral(String.valueOf(currentPage * batchSize))))
                        .executeQuery(ConnectionManager.getConnection(), ConnectionManager.createSqlVisitor(), getMapper());
                Preloader<E> preloader = new Preloader<>();
                preloader.preload(records, preloadRelations);
                currentPage++;
                return records;
            }

            @Override
            public E next() {
                return list.next();
            }
        };
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false);
    }

    public abstract Select.QueryResultMapper<E> getMapper();
}
