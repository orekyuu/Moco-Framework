package net.orekyuu.moco.feeling.node;

import net.orekyuu.moco.feeling.SqlContext;
import net.orekyuu.moco.feeling.Table;
import net.orekyuu.moco.feeling.visitor.SqlVisitor;

public class SqlJoin extends SqlBinary {
    private final Table table;

    public SqlJoin(Table table, SqlNodeExpression expression) {
        super(new SqlLiteral(table.getTableName()), new SqlOn(expression));
        this.table = table;
    }

    @Override
    public void accept(SqlVisitor visitor, SqlContext context) {
        visitor.visit(this, context);
    }

    public SqlLiteral tableLiteral() {
        return (SqlLiteral) left();
    }

    public SqlNode expression() {
        return right();
    }

    public Table table() {
        return table;
    }
}
