package net.orekyuu.moco.chou.entity;

import com.squareup.javapoet.CodeBlock;
import net.orekyuu.moco.core.attribute.Attribute;
import net.orekyuu.moco.core.attribute.BooleanAttribute;
import net.orekyuu.moco.core.attribute.IntAttribute;
import net.orekyuu.moco.core.attribute.StringAttribute;

import javax.lang.model.element.VariableElement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

public enum DatabaseColumnType {
    INT(IntAttribute.class, "intCol") {
        @Override
        boolean isSupport(VariableElement fieldType) {
            return Arrays.asList(int.class.getName(), Integer.class.getName()).contains(fieldType.asType().toString());
        }

        @Override
        void addColumnMethod(CodeBlock.Builder codeBlock, String columnName) {
            codeBlock.add("._integer($S)", columnName);
        }
    },LONG(IntAttribute.class, "intCol") {
        @Override
        boolean isSupport(VariableElement fieldType) {
            return Arrays.asList(long.class.getName(), Long.class.getName()).contains(fieldType.asType().toString());
        }

        @Override
        void addColumnMethod(CodeBlock.Builder codeBlock, String columnName) {
            codeBlock.add("._decimal($S)", columnName);
        }
    },STRING(StringAttribute.class, "stringCol") {
        @Override
        boolean isSupport(VariableElement fieldType) {
            return Arrays.asList(String.class.getName()).contains(fieldType.asType().toString());
        }

        @Override
        void addColumnMethod(CodeBlock.Builder codeBlock, String columnName) {
            codeBlock.add("._string($S)", columnName);
        }
    },BOOLEAN(BooleanAttribute.class, "booleanCol") {
        @Override
        boolean isSupport(VariableElement fieldType) {
            return Arrays.asList(boolean.class.getName(), Boolean.class.getName()).contains(fieldType.asType().toString());
        }
        @Override
        void addColumnMethod(CodeBlock.Builder codeBlock, String columnName) {
            codeBlock.add("._boolean($S)", columnName);
        }
    },DOUBLE(StringAttribute.class, "stringCol") {
        @Override
        boolean isSupport(VariableElement fieldType) {
            return Arrays.asList(float.class.getName(), Float.class.getName(), double.class.getName(), Double.class.getName())
                    .contains(fieldType.asType().toString());
        }

        @Override
        void addColumnMethod(CodeBlock.Builder codeBlock, String columnName) {
            codeBlock.add("._decimal($S)", columnName);
        }
    },DATE(StringAttribute.class, "timeCol") {
        @Override
        boolean isSupport(VariableElement fieldType) {
            return Arrays.asList(LocalDate.class.getName()).contains(fieldType.asType().toString());
        }
        @Override
        void addColumnMethod(CodeBlock.Builder codeBlock, String columnName) {
            codeBlock.add("._datetime($S)", columnName);
        }
    },DATETIME(StringAttribute.class, "timeCol") {
        @Override
        boolean isSupport(VariableElement fieldType) {
            return Arrays.asList(LocalDateTime.class.getName()).contains(fieldType.asType().toString());
        }
        @Override
        void addColumnMethod(CodeBlock.Builder codeBlock, String columnName) {
            codeBlock.add("._datetime($S)", columnName);
        }
    };

    DatabaseColumnType(Class<? extends Attribute> attribute, String feelingTableMethod) {
        this.attribute = attribute;
        this.feelingTableMethod = feelingTableMethod;
    }

    private final Class<? extends Attribute> attribute;
    private final String feelingTableMethod;


    abstract boolean isSupport(VariableElement fieldType);

    abstract void addColumnMethod(CodeBlock.Builder codeBlock, String columnName);

    public static Optional<DatabaseColumnType> findSupportedType(VariableElement fieldType) {
        for (DatabaseColumnType columnType : values()) {
            if (columnType.isSupport(fieldType)) {
                return Optional.of(columnType);
            }
        }
        return Optional.empty();
    }

    public Class<? extends Attribute> getAttribute() {
        return attribute;
    }

    public String getFeelingTableMethod() {
        return feelingTableMethod;
    }
}
