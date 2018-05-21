package net.orekyuu.moco.feeling.node;

import net.orekyuu.moco.feeling.SqlContext;
import net.orekyuu.moco.feeling.visitor.SqlVisitor;

public class SqlBindParam<T> extends SqlNodeExpression {
    private final Class<T> clazz;
    private final T value;

    public SqlBindParam(T value, Class<T> clazz) {
        this.value = value;
        this.clazz = clazz;
    }

    public T getValue() {
        return value;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    @Override
    public void accept(SqlVisitor visitor, SqlContext context) {
        visitor.visit(this, context);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SqlBindParam{");
        sb.append("clazz=").append(clazz);
        sb.append(", value=").append(value);
        sb.append('}');
        return sb.toString();
    }
}
