package net.orekyuu.moco.feeling;

public class IntColumn extends ColumnAdapter {
    public IntColumn(String name, String table) {
        super(name, table);
    }

    public Predicate eq(int i) {
        return null;
    }
}
