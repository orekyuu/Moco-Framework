package net.orekyuu.moco.feeling.exposer;

public class RawConverter<T> implements Converter<T, T> {
    @Override
    public T fromDB(T dbValue) {
        return dbValue;
    }

    @Override
    public T toDBValue(T o) {
        return o;
    }
}
