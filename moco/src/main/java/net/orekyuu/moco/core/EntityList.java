package net.orekyuu.moco.core;

import net.orekyuu.moco.core.attribute.Predicate;
import net.orekyuu.moco.core.relation.Preloader;
import net.orekyuu.moco.core.relation.Relation;
import net.orekyuu.moco.feeling.Delete;
import net.orekyuu.moco.feeling.Select;
import net.orekyuu.moco.feeling.Table;
import net.orekyuu.moco.feeling.node.*;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public abstract class EntityList<T extends EntityList<T, E>, E> {

    protected Table table;
    protected Optional<WhereClause> whereClause = Optional.empty();
    protected Optional<SqlLimit> sqlLimit = Optional.empty();
    protected Optional<SqlOffset> sqlOffset = Optional.empty();
    private List<Relation<E>> preloadRelations = new ArrayList<>();
    private List<SqlOrderigTerm> orders = new ArrayList<>();

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
        return delete;
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
        sqlLimit = Optional.of(new SqlLimit(new SqlBindParam(limit, Integer.class)));
        return (T)this;
    }

    public T limitAndOffset(int limit, int offset) {
        limit(limit);
        sqlOffset = Optional.of(new SqlOffset(new SqlBindParam(limit, Integer.class)));
        return (T)this;
    }

    public T preload(Relation<E> relation) {
        preloadRelations.add(relation);
        return (T)this;
    }

    public void delete() {
        createDelete().executeQuery(ConnectionManager.getConnection());
    }

    public List<E> toList() {
        List<E> records = createSelect().executeQuery(ConnectionManager.getConnection(), getMapper());
        Preloader<E> preloader = new Preloader<>();
        preloader.preload(records, preloadRelations);
        return records;
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
                        .executeQuery(ConnectionManager.getConnection(), getMapper());
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
