package net.orekyuu.moco.feeling.node;

import net.orekyuu.moco.feeling.SqlContext;
import net.orekyuu.moco.feeling.visitor.SqlVisitor;

public class SqlJoin extends SqlBinary {
    public SqlJoin(SqlLiteral table, SqlNodeExpression expression) {
        super(table, new SqlOn(expression));
    }

    @Override
    public void accept(SqlVisitor visitor, SqlContext context) {
        visitor.visit(this, context);
    }

    public SqlLiteral table() {
        return (SqlLiteral) left();
    }

    public SqlNode expression() {
        return right();
    }
}
