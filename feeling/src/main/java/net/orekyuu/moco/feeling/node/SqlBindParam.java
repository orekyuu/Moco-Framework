package net.orekyuu.moco.feeling.node;

public class SqlBindParam implements SqlNode {
    private final Class<?> clazz;
    private final Object value;

    public SqlBindParam(Object value, Class<?> clazz) {
        if (!clazz.isInstance(value)) {
            throw new ClassCastException();
        }
        this.value = value;
        this.clazz = clazz;
    }

    public Object getValue() {
        return value;
    }

    @SuppressWarnings("unchecked")
    public <T> T getValue(Class<T> clazz) {
        return (T) value;
    }

    public Class<?> getClazz() {
        return clazz;
    }
}
