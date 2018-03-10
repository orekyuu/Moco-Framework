package net.orekyuu.moco.feeling.attributes;

import net.orekyuu.moco.feeling.SqlContext;
import net.orekyuu.moco.feeling.visitor.SqlVisitor;

public class BooleanAttribute extends Attribute {
    public BooleanAttribute(String relation, String name) {
        super(relation, name);
    }

    @Override
    public void accept(SqlVisitor visitor, SqlContext context) {
        visitor.visit(this, context);
    }
}
