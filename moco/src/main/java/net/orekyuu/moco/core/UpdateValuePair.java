package net.orekyuu.moco.core;

import net.orekyuu.moco.core.attribute.Attribute;
import net.orekyuu.moco.feeling.node.SqlNode;

public class UpdateValuePair<E, V> {
    private final SqlNode sqlNode;
    private Attribute<E, V> attribute;

    private UpdateValuePair(Attribute<E, V> attribute, SqlNode sqlNode) {
        this.attribute = attribute;
        this.sqlNode = sqlNode;
    }

    public static <E, V> UpdateValuePair<E, V> of(Attribute<E, V> attribute, SqlNode sqlNode) {
        return new UpdateValuePair<>(attribute, sqlNode);
    }

    public Attribute<E, V> getAttribute() {
        return attribute;
    }

    public SqlNode getSqlNode() {
        return sqlNode;
    }
}
