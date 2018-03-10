package net.orekyuu.moco.feeling.node;

import net.orekyuu.moco.feeling.SqlContext;
import net.orekyuu.moco.feeling.visitor.SqlVisitor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SqlNodeArray implements SqlNode {
    private final List<SqlNode> nodes;

    public SqlNodeArray(Collection<? extends SqlNode> nodes) {
        this.nodes = new ArrayList<>(nodes);
    }

    @Override
    public void accept(SqlVisitor visitor, SqlContext context) {
        visitor.visit(this, context);
    }

    public List<SqlNode> getNodes() {
        return nodes;
    }
}
