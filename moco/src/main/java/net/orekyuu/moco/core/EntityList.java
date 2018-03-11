package net.orekyuu.moco.core;

import net.orekyuu.moco.core.attribute.Predicate;
import net.orekyuu.moco.feeling.Select;
import net.orekyuu.moco.feeling.node.SqlNodeExpression;
import net.orekyuu.moco.feeling.node.WhereClause;

import java.util.Arrays;

public abstract class EntityList<T extends EntityList<T>> {

    protected Select select;
    public EntityList(Select select) {
        this.select = select;
    }

    public T where(Predicate... predicates) {
        Arrays.stream(predicates).forEach(this::where);
        return (T) this;
    }

    public T where(Predicate predicate) {
        WhereClause whereClause = select.getWhereClause();
        if (whereClause == null) {
            select.where(new WhereClause(predicate.getExpression()));
        } else {
            SqlNodeExpression expression = whereClause.getExpression().and(predicate.getExpression());
            whereClause.setExpression(expression);
        }
        return (T)this;
    }
}
