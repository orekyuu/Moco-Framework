package net.orekyuu.moco.feeling.node;

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
}
