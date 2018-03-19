package net.orekyuu.moco.feeling.node;

public abstract class SqlUnary extends SqlNodeExpression {
    private final SqlNode node;

    public SqlUnary(SqlNode node) {
        this.node = node;
    }

    public SqlNode node() {
        return node;
    }
}
