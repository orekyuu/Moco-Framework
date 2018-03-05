package net.orekyuu.moco.feeling;

import java.util.*;

public class TableBuilder {

    private final String tableName;
    private final Set<Column<?>> attrs = new HashSet<>();

    public TableBuilder(String tableName) {
        this.tableName = Objects.requireNonNull(tableName);
    }

    public TableBuilder integer(String name) {
        attrs.add(new IntColumn(name, tableName));
        return this;
    }

    public Table build() {
        return new Table(tableName, attrs);
    }

}
