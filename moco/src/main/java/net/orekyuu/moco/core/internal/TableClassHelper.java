package net.orekyuu.moco.core.internal;

import net.orekyuu.moco.core.attribute.Attribute;
import net.orekyuu.moco.feeling.node.SqlBindParam;

import java.lang.reflect.Field;
import java.util.function.Function;

public final class TableClassHelper {
    public static Field getDeclaredField(Class<?> clazz, String fieldName) throws NoSuchFieldException {
        Field f = clazz.getDeclaredField(fieldName);
        f.setAccessible(true);
        return f;
    }

    @SuppressWarnings("unchecked")
    public static <E, V> SqlBindParam createBindParam(Attribute<E, V> attribute, E entity) {
        return new SqlBindParam(attribute.getAccessor().get(entity), attribute.bindType());
    }

    @SuppressWarnings("unchecked")
    public static <E, T1, T2> SqlBindParam createBindParam(Attribute<E, T1> attribute, E entity, Function<T1, T2> converter) {
        T1 value = (T1) attribute.getAccessor().get(entity);
        T2 convertedValue = value == null ? null : converter.apply(value);
        return new SqlBindParam(convertedValue, attribute.bindType());
    }
}
