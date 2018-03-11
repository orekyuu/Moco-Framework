package net.orekyuu.moco.core.attribute;

import net.orekyuu.moco.feeling.node.SqlNodeExpression;

public class Predicate {

    private SqlNodeExpression expression;

    public Predicate(SqlNodeExpression expression) {
        this.expression = expression;
    }

    public SqlNodeExpression getExpression() {
        return expression;
    }
}
