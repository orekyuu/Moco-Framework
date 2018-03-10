package net.orekyuu.moco.feeling.node;

public abstract class SqlBinaryExpression extends SqlNodeExpression {

    protected SqlNode left;
    protected SqlNode right;

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
