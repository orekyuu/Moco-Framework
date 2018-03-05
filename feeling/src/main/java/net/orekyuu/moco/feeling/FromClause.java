package net.orekyuu.moco.feeling;

public class FromClause {

    private final Table table;

    public FromClause(Table table) {
        this.table = table;
    }

    public void generateSql(SqlContext context) {
        context.append(table.getTableName()).append(" ");
    }
}
