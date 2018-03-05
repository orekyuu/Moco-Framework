package net.orekyuu.moco.feeling;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

public class Table {
    private final String tableName;
    private final Set<Column<?>> columns;
    private final HashMap<ClassColumnPair, Column> columnMap = new HashMap<>();


    Table(String tableName, Set<Column<?>> columns) {
        this.tableName = tableName;
        this.columns = columns;

        for (Column<?> column : columns) {
            ClassColumnPair pair = new ClassColumnPair(column.getClass(), column.name());
            columnMap.put(pair, column);
        }
    }

    public String getTableName() {
        return tableName;
    }

    public Select select() {
        Select select = new Select();
        select.select(columns).from(this);
        return select;
    }

    public IntColumn intCol(String name) {
        return findColumn(IntColumn.class, name);
    }

    private <T extends Column<?>> T findColumn(Class<T> clazz, String name) {
        return (T) columnMap.get(new ClassColumnPair(clazz, name));
    }

    private static class ClassColumnPair {
        private Class clazz;
        private String name;

        ClassColumnPair(Class clazz, String name) {
            this.clazz = clazz;
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ClassColumnPair that = (ClassColumnPair) o;
            return Objects.equals(clazz, that.clazz) &&
                    Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(clazz, name);
        }
    }

}
