package net.orekyuu.moco.core.attribute;

public abstract class Attribute {
    protected net.orekyuu.moco.feeling.attributes.Attribute attribute;

    public Attribute(net.orekyuu.moco.feeling.attributes.Attribute attribute) {
        this.attribute = attribute;
    }

    public net.orekyuu.moco.feeling.attributes.Attribute ast() {
        return attribute;
    }
}
