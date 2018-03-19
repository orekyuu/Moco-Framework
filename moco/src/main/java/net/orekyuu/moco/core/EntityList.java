package net.orekyuu.moco.core;

import net.orekyuu.moco.core.attribute.Predicate;
import net.orekyuu.moco.core.relation.Preloader;
import net.orekyuu.moco.core.relation.Relation;
import net.orekyuu.moco.feeling.Select;
import net.orekyuu.moco.feeling.node.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class EntityList<T extends EntityList<T, E>, E> {

    protected Select select;
    private List<Relation<E>> preloadRelations = new ArrayList<>();

    public EntityList(Select select) {
        this.select = select;
    }

    public T where(Predicate... predicates) {
        Arrays.stream(predicates).forEach(this::where);
        return (T) this;
    }

    public T where(Predicate predicate) {
        WhereClause whereClause = select.getWhereClause().orElse(null);
        if (whereClause == null) {
            select.where(new WhereClause(predicate.getExpression()));
        } else {
            SqlNodeExpression expression = whereClause.getExpression().and(predicate.getExpression());
            whereClause.setExpression(expression);
        }
        return (T)this;
    }

    public T limit(int limit) {
        select.limit(new SqlLimit(new SqlBindParam(limit, Integer.class)));
        return (T)this;
    }

    public T limitAndOffset(int limit, int offset) {
        limit(limit);
        select.offset(new SqlOffset(new SqlBindParam(offset, Integer.class)));
        return (T)this;
    }

    public T preload(Relation<E> relation) {
        preloadRelations.add(relation);
        return (T)this;
    }

    public List<E> toList() {
        List<E> records = select.executeQuery(ConnectionManager.getConnection(), getMapper());
        Preloader<E> preloader = new Preloader<>();
        preloader.preload(records, preloadRelations);
        return records;
    }

    public abstract Select.QueryResultMapper<E> getMapper();
}
