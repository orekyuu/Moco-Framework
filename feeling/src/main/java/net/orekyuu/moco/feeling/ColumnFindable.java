package net.orekyuu.moco.feeling;

import net.orekyuu.moco.feeling.attributes.*;

public interface ColumnFindable {

    default IntAttribute intCol(String name) {
        return findColumn(IntAttribute.class, name);
    }

    default StringAttribute stringCol(String name) {
        return findColumn(StringAttribute.class, name);
    }

    default BooleanAttribute booleanCol(String name) {
        return findColumn(BooleanAttribute.class, name);
    }

    default DecimalAttribute decimalCol(String name) {
        return findColumn(DecimalAttribute.class, name);
    }

    default FloatAttribute floatCol(String name) {
        return findColumn(FloatAttribute.class, name);
    }

    default TimeAttribute timeCol(String name) {
        return findColumn(TimeAttribute.class, name);
    }

    <T extends Attribute> T findColumn(Class<T> clazz, String name);
}
