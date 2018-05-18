package net.orekyuu.moco.feeling;

import net.orekyuu.moco.feeling.attributes.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class TableBuilder {

    private final String tableName;
    private final Select.QueryResultMapper mapper;
    private final Set<Attribute> attrs = new HashSet<>();

    public TableBuilder(String tableName, Select.QueryResultMapper mapper) {
        this.tableName = Objects.requireNonNull(tableName);
        this.mapper = mapper;
    }

    public TableBuilder _integer(String name) {
        attrs.add(new IntAttribute(tableName, name));
        return this;
    }

    public TableBuilder _long(String name) {
        attrs.add(new LongAttribute(tableName, name));
        return this;
    }

    public TableBuilder _string(String name) {
        attrs.add(new StringAttribute(tableName, name));
        return this;
    }

    public TableBuilder _datetime(String name) {
        attrs.add(new TimeAttribute(tableName, name));
        return this;
    }

    public TableBuilder _boolean(String name) {
        attrs.add(new BooleanAttribute(tableName, name));
        return this;
    }

    public TableBuilder _decimal(String name) {
        attrs.add(new DecimalAttribute(tableName, name));
        return this;
    }

    public TableBuilder _float(String name) {
        attrs.add(new FloatAttribute(tableName, name));
        return this;
    }

    public Table build() {
        return new Table(tableName, attrs);
    }

}
