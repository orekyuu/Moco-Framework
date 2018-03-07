package net.orekyuu.moco.feeling.node;

public class SqlNodeExpression implements SqlNode, Predicatable {

    public SqlNodeExpression and(SqlNodeExpression right) {
        return new SqlAnd(this, right);
    }

    public SqlNodeExpression or(SqlNodeExpression right) {
        return new SqlOr(this, right);
    }
}
