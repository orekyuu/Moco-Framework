package net.orekyuu.moco.feeling.node;

import net.orekyuu.moco.feeling.SqlContext;

public class WhereClause {

    private SqlNodeExpression expression;

    public WhereClause(SqlNodeExpression expression) {
        this.expression = expression;
    }

    public SqlNodeExpression getExpression() {
        return expression;
    }

    public void generateSql(SqlContext context) {

    }
}
