package net.orekyuu.moco.feeling;

public interface Column<T> {
    String name();

    String tableName();

    String fullColumnName();
}
