package net.orekyuu.moco.feeling.node;

import net.orekyuu.moco.feeling.SqlContext;
import net.orekyuu.moco.feeling.attributes.Attribute;
import net.orekyuu.moco.feeling.visitor.SqlVisitor;

public class SqlColumnNameExprPair implements SqlNode {
    private final Attribute attribute;
    private final SqlNode sqlNode;

    public SqlColumnNameExprPair(Attribute attribute, SqlNode sqlNode) {
        this.attribute = attribute;
        this.sqlNode = sqlNode;
    }

    public Attribute getAttribute() {
        return attribute;
    }

    public SqlNode getSqlNode() {
        return sqlNode;
    }

    @Override
    public void accept(SqlVisitor visitor, SqlContext context) {
        visitor.visit(this, context);
    }
}
