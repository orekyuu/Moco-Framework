package net.orekyuu.moco.feeling;

import java.util.Objects;

public abstract class ColumnAdapter<T> implements Column<T> {
    private final String name;
    private final String table;

    public ColumnAdapter(String name, String table) {
        this.name = Objects.requireNonNull(name);
        this.table = Objects.requireNonNull(table);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String tableName() {
        return table;
    }

    @Override
    public String fullColumnName() {
        return String.join(".", tableName(), name());
    }
}
