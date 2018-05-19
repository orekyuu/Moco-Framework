package net.orekyuu.moco.core;

import net.orekyuu.moco.core.attribute.Attribute;
import net.orekyuu.moco.feeling.node.SqlNode;

public class UpdateValuePair<E> {
    private final SqlNode sqlNode;
    private Attribute<E> attribute;

    private UpdateValuePair(Attribute<E> attribute, SqlNode sqlNode) {
        this.attribute = attribute;
        this.sqlNode = sqlNode;
    }

    public static <E> UpdateValuePair<E> of(Attribute<E> attribute, SqlNode sqlNode) {
        return new UpdateValuePair<>(attribute, sqlNode);
    }

    public Attribute<E> getAttribute() {
        return attribute;
    }

    public SqlNode getSqlNode() {
        return sqlNode;
    }
}
