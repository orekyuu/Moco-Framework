package net.orekyuu.moco.feeling.node;

public abstract class SqlBinaryExpression extends SqlNodeExpression {

    protected final SqlNode left;
    protected final SqlNode right;

    public SqlBinaryExpression(SqlNode left, SqlNode right) {
        this.left = left;
        this.right = right;
    }

    public SqlNode left() {
        return left;
    }

    public SqlNode right() {
        return right;
    }
}
