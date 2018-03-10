package net.orekyuu.moco.feeling.attributes;

import net.orekyuu.moco.feeling.node.Predicatable;

public abstract class Attribute implements Predicatable {
    private String relation;
    private String name;

    public Attribute(String relation, String name) {
        this.relation = relation;
        this.name = name;
    }

    public String getRelation() {
        return relation;
    }

    public String getName() {
        return name;
    }
}
