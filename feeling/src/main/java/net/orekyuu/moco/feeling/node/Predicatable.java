package net.orekyuu.moco.feeling.node;

import java.util.Collection;

public interface Predicatable extends SqlNode {

    default SqlNodeExpression gt(SqlNode right) {
        return new SqlGt(this, right);
    }

    default SqlNodeExpression gteq(SqlNode right) {
        return new SqlGteq(this, right);
    }

    default SqlNodeExpression lt(SqlNode right) {
        return new SqlLt(this, right);
    }

    default SqlNodeExpression lteq(SqlNode right) {
        return new SqlLteq(this, right);
    }

    default SqlNodeExpression eq(SqlNode right) {
        return new SqlEq(this, right);
    }

    default SqlNodeExpression noteq(SqlNode right) {
        return new SqlNoteq(this, right);
    }

    default SqlNodeExpression in(SqlNode right) {
        return new SqlIn(this, right);
    }

    default SqlNodeExpression in(Collection<? extends SqlNode> right) {
        return in(new SqlNodeArray(right));
    }

    default SqlNodeExpression in(SqlNodeArray right) {
        return new SqlIn(this, right);
    }
}
