package net.orekyuu.moco.feeling.exposer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Exposer<DB, ENTITY_VALUE> {
    private final DatabaseColumnType columnType;
    private final Converter<DB, ENTITY_VALUE> converter;

    public Exposer(DatabaseColumnType columnType, Converter<DB, ENTITY_VALUE> converter) {
        this.columnType = columnType;
        this.converter = converter;
    }

    public ENTITY_VALUE expose(ResultSet resultSet, String column) throws SQLException {
        Object value = fetchValue(resultSet, column);

        @SuppressWarnings("unchecked")
        DB dbValue = (DB) value;

        return converter.fromDB(dbValue);
    }

    public Object fetchValue(ResultSet resultSet, String column) throws SQLException {
        switch (columnType) {
            case BYTE: return resultSet.getByte(column);
            case INT: return resultSet.getInt(column);
            case SHORT: return resultSet.getShort(column);
            case STRING: return resultSet.getString(column);
            case BOOLEAN: return resultSet.getBoolean(column);
            case DATE: return resultSet.getDate(column);
            case DOUBLE: return resultSet.getDouble(column);
            case FLOAT: return resultSet.getFloat(column);
            case LONG: return resultSet.getLong(column);
            case TIME: return resultSet.getTime(column);
            case TIMESTAMP: return resultSet.getTimestamp(column);
            case OBJECT: return resultSet.getObject(column);
        }
        throw new IllegalStateException("Unsupported columnType: " + columnType.name());
    }
}
