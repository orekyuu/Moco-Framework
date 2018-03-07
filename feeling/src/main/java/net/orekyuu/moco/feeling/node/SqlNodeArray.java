package net.orekyuu.moco.feeling.node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SqlNodeArray implements SqlNode {
    private final List<SqlNode> nodes;

    public SqlNodeArray(Collection<? extends SqlNode> nodes) {
        this.nodes = new ArrayList<>(nodes);
    }
}
