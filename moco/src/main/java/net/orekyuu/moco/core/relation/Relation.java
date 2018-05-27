package net.orekyuu.moco.core.relation;

import net.orekyuu.moco.core.attribute.Attribute;
import net.orekyuu.moco.feeling.Table;

import java.util.List;

public abstract class Relation<OWNER> {
    protected final Table owner;
    protected final Attribute ownerKeyAttribute;
    protected final Table child;
    protected final Attribute childKeyAttribute;

    public Relation(Table owner, Attribute ownerKeyAttribute, Table child, Attribute childKeyAttribute) {
        this.owner = owner;
        this.ownerKeyAttribute = ownerKeyAttribute;
        this.child = child;
        this.childKeyAttribute = childKeyAttribute;
    }

    public abstract void preload(List<OWNER> records);
}
