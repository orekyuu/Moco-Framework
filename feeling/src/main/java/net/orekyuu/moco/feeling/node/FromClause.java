package net.orekyuu.moco.feeling.node;

import net.orekyuu.moco.feeling.SqlContext;

public class FromClause {

    private final SqlJoinClause sqlJoinClause;

    public FromClause(SqlJoinClause sqlJoinClause) {
        this.sqlJoinClause = sqlJoinClause;
    }

    public void generateSql(SqlContext context) {

    }
}
