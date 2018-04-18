package net.orekyuu.moco.chou;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import net.orekyuu.moco.feeling.exposer.DatabaseColumnType;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class TypeUtils {

    public static DatabaseColumnType findByType(TypeName className) {
        if (ClassName.get(Byte.class).equals(className) || TypeName.BYTE.equals(className)) {
            return DatabaseColumnType.BYTE;
        }
        if (ClassName.get(Integer.class).equals(className) || TypeName.INT.equals(className)) {
            return DatabaseColumnType.INT;
        }
        if (ClassName.get(Short.class).equals(className) || TypeName.SHORT.equals(className)) {
            return DatabaseColumnType.SHORT;
        }
        if (ClassName.get(String.class).equals(className)) {
            return DatabaseColumnType.STRING;
        }
        if (ClassName.get(Boolean.class).equals(className) || TypeName.BOOLEAN.equals(className)) {
            return DatabaseColumnType.BOOLEAN;
        }
        if (ClassName.get(Date.class).equals(className)) {
            return DatabaseColumnType.DATE;
        }
        if (ClassName.get(Double.class).equals(className) || TypeName.DOUBLE.equals(className)) {
            return DatabaseColumnType.DOUBLE;
        }
        if (ClassName.get(Float.class).equals(className) || TypeName.FLOAT.equals(className)) {
            return DatabaseColumnType.FLOAT;
        }
        if (ClassName.get(Long.class).equals(className) || TypeName.LONG.equals(className)) {
            return DatabaseColumnType.LONG;
        }
        if (ClassName.get(Time.class).equals(className)) {
            return DatabaseColumnType.TIME;
        }
        if (ClassName.get(Timestamp.class).equals(className)) {
            return DatabaseColumnType.TIMESTAMP;
        }
        return DatabaseColumnType.OBJECT;
    }
}
