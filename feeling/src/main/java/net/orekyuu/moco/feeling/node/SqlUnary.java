package net.orekyuu.moco.feeling.node;

public abstract class SqlUnary extends SqlNodeExpression {
    private final SqlNodeExpression expression;

    public SqlUnary(SqlNodeExpression expression) {
        this.expression = expression;
    }

    public SqlNodeExpression expression() {
        return expression;
    }
}
