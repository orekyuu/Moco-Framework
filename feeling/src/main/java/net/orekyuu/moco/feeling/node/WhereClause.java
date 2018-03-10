package net.orekyuu.moco.feeling.node;

import net.orekyuu.moco.feeling.SqlContext;
import net.orekyuu.moco.feeling.visitor.SqlVisitor;

public class WhereClause implements SqlNode {

    private SqlNodeExpression expression;

    public WhereClause(SqlNodeExpression expression) {
        this.expression = expression;
    }

    public SqlNodeExpression getExpression() {
        return expression;
    }

    @Override
    public void accept(SqlVisitor visitor, SqlContext context) {
        visitor.visit(this, context);
    }
}
