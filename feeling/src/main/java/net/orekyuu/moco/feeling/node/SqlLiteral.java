package net.orekyuu.moco.feeling.node;

import net.orekyuu.moco.feeling.SqlContext;
import net.orekyuu.moco.feeling.visitor.SqlVisitor;

public class SqlLiteral implements SqlNode, Predicatable {
    private final String text;

    public SqlLiteral(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public void accept(SqlVisitor visitor, SqlContext context) {
        visitor.visit(this, context);
    }
}
