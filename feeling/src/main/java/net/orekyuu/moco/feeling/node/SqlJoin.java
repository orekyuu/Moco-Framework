package net.orekyuu.moco.feeling.node;

public abstract class SqlJoin extends SqlBinary {
    public SqlJoin(SqlLiteral table, SqlNodeExpression table2) {
        super(table, table2);
    }
}
