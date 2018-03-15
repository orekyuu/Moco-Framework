package net.orekyuu.moco.core.attribute;

@FunctionalInterface
public interface AttributeValueAccessor<R> {
    Object get(R record);
}
