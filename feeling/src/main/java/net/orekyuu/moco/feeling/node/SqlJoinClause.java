package net.orekyuu.moco.feeling.node;

import net.orekyuu.moco.feeling.SqlContext;
import net.orekyuu.moco.feeling.Table;
import net.orekyuu.moco.feeling.visitor.SqlVisitor;

import java.util.ArrayList;
import java.util.List;

public class SqlJoinClause extends SqlNodeExpression {

    private final Table table;
    private final SqlNode singleSourceNode;
    private final List<SqlJoin> joins = new ArrayList<>();

    public SqlJoinClause(Table table) {
        this.table = table;
        singleSourceNode = new SqlLiteral(table.getTableName());
    }

    public boolean isEmpty() {
        return joins.isEmpty();
    }

    public void add(SqlJoin join) {
        joins.add(join);
    }

    @Override
    public void accept(SqlVisitor visitor, SqlContext context) {
        visitor.visit(this, context);
    }

    public SqlNode getSingleSourceNode() {
        return singleSourceNode;
    }

    public Table getTable() {
        return table;
    }

    public List<SqlJoin> getJoins() {
        return joins;
    }
}
