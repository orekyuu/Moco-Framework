package net.orekyuu.moco.feeling.node;

public class SqlJoin extends SqlBinary {
    public SqlJoin(SqlLiteral table, SqlNodeExpression expression) {
        super(table, new SqlOn(expression));
    }
}
