package net.orekyuu.moco.feeling.node;

public class SqlJoinSource extends SqlBinary {
    public SqlJoinSource(SqlNode singleSource) {
        super(singleSource, null);
    }

    public boolean isEmpty() {
        return left != null && right == null;
    }

    public void add(SqlJoin join) {

    }
}
