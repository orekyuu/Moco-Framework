package net.orekyuu.moco.feeling.node;

public abstract class SqlBinary extends SqlBinaryExpression {
    public SqlBinary(SqlNode left, SqlNode right) {
        super(left, right);
    }
}
