package net.orekyuu.moco.feeling.node;

import net.orekyuu.moco.feeling.SqlContext;
import net.orekyuu.moco.feeling.visitor.SqlVisitor;

public class SqlOffset extends SqlUnary {
    public SqlOffset(SqlNodeExpression expression) {
        super(expression);
    }

    @Override
    public void accept(SqlVisitor visitor, SqlContext context) {
        visitor.visit(this, context);
    }
}
