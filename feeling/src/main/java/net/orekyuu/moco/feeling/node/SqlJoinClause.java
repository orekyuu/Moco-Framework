package net.orekyuu.moco.feeling.node;

import net.orekyuu.moco.feeling.SqlContext;
import net.orekyuu.moco.feeling.visitor.SqlVisitor;

import java.util.ArrayList;
import java.util.List;

public class SqlJoinClause extends SqlNodeExpression {

    private final SqlNode singleSource;
    private final List<SqlJoin> joins = new ArrayList<>();

    public SqlJoinClause(SqlNode singleSource) {
        this.singleSource = singleSource;
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

    public SqlNode getSingleSource() {
        return singleSource;
    }

    public List<SqlJoin> getJoins() {
        return joins;
    }
}
