package net.orekyuu.moco.feeling.node;

import net.orekyuu.moco.feeling.SqlContext;
import net.orekyuu.moco.feeling.visitor.SqlVisitor;

public class SqlInnerJoin extends SqlJoin {
    public SqlInnerJoin(SqlLiteral table, SqlNodeExpression table2) {
        super(table, table2);
    }

    @Override
    public void accept(SqlVisitor visitor, SqlContext context) {
        visitor.visit(this, context);
    }
}
