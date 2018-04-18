package net.orekyuu.moco.feeling.exposer;

public interface Converter <DB, ENTITY_VALUE> {
    ENTITY_VALUE fromDB(DB dbValue);

    DB toDBValue(ENTITY_VALUE entityValue);

    static <T> RawConverter<T> raw() {
        return new RawConverter<>();
    }
}
