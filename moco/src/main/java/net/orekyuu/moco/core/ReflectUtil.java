package net.orekyuu.moco.core;

import java.lang.reflect.Field;

public class ReflectUtil {

    public interface FieldSetter {
        void set(Object instance, Object value) throws ReflectiveOperationException;
    }

    public static FieldSetter getFieldSetter(Class clazz, String field) {
        return new FieldSetter() {

            private Field declaredField;

            {
                try {
                    declaredField = clazz.getDeclaredField(field);
                    declaredField.setAccessible(true);
                } catch (ReflectiveOperationException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void set(Object instance, Object value) throws ReflectiveOperationException {
                declaredField.set(instance, value);
            }
        };
    }
}
