package net.orekyuu.moco.feeling;

import net.orekyuu.moco.feeling.attributes.Attribute;
import net.orekyuu.moco.feeling.attributes.IntAttribute;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class TableBuilder {

    private final String tableName;
    private final Set<Attribute> attrs = new HashSet<>();

    public TableBuilder(String tableName) {
        this.tableName = Objects.requireNonNull(tableName);
    }

    public TableBuilder integer(String name) {
        attrs.add(new IntAttribute(tableName, name));
        return this;
    }

    public Table build() {
        return new Table(tableName, attrs);
    }

}
