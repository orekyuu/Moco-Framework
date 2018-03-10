package net.orekyuu.moco.feeling;

import net.orekyuu.moco.feeling.attributes.Attribute;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Table implements ColumnFindable {
    private final String tableName;
    private final Set<Attribute> columns;
    private final HashMap<ClassColumnPair, Attribute> columnMap = new HashMap<>();


    Table(String tableName, Set<Attribute> columns) {
        this.tableName = tableName;
        this.columns = columns;

        for (Attribute column : columns) {
            ClassColumnPair pair = new ClassColumnPair(column.getClass(), column.getName());
            columnMap.put(pair, column);
        }
    }

    public String getTableName() {
        return tableName;
    }

    @Override
    public <T extends Attribute> T findColumn(Class<T> clazz, String name) {
        return (T) columnMap.get(new ClassColumnPair(clazz, name));
    }

    public Set<Attribute> getColumns() {
        return new HashSet<>(columns);
    }

    public Select select() {
        return new Select().from(this);
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
