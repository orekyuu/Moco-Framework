package net.orekyuu.moco.feeling.node;

import net.orekyuu.moco.feeling.SqlContext;
import net.orekyuu.moco.feeling.visitor.SqlVisitor;

public class FromClause implements SqlNode {

    private final SqlJoinClause sqlJoinClause;

    public FromClause(SqlJoinClause sqlJoinClause) {
        this.sqlJoinClause = sqlJoinClause;
    }

    @Override
    public void accept(SqlVisitor visitor, SqlContext context) {
        visitor.visit(this, context);
    }

    public SqlJoinClause getSqlJoinClause() {
        return sqlJoinClause;
    }
}
