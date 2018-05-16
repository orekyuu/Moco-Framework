package net.orekyuu.moco.chou.entity;

import com.squareup.javapoet.CodeBlock;
import net.orekyuu.moco.chou.AnnotationProcessHelper;
import net.orekyuu.moco.chou.RoundContext;
import net.orekyuu.moco.core.attribute.*;

import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

public enum DatabaseColumnType {
    INT(IntAttribute.class, "intCol", "getInt") {
        @Override
        public boolean isSupport(RoundContext context, VariableElement fieldType) {
            return Arrays.asList(int.class.getName(), Integer.class.getName()).contains(fieldType.asType().toString());
        }

        @Override
        void addColumnMethod(RoundContext context, CodeBlock.Builder codeBlock, String columnName) {
            codeBlock.add("._integer($S)", columnName);
        }
    },LONG(IntAttribute.class, "intCol", "getInt") {
        @Override
        public boolean isSupport(RoundContext context, VariableElement fieldType) {
            return Arrays.asList(long.class.getName(), Long.class.getName()).contains(fieldType.asType().toString());
        }

        @Override
        void addColumnMethod(RoundContext context, CodeBlock.Builder codeBlock, String columnName) {
            codeBlock.add("._decimal($S)", columnName);
        }
    },STRING(StringAttribute.class, "stringCol", "getString") {
        @Override
        public boolean isSupport(RoundContext context, VariableElement fieldType) {
            return Arrays.asList(String.class.getName()).contains(fieldType.asType().toString());
        }

        @Override
        void addColumnMethod(RoundContext context, CodeBlock.Builder codeBlock, String columnName) {
            codeBlock.add("._string($S)", columnName);
        }
    },BOOLEAN(BooleanAttribute.class, "booleanCol", "getBoolean") {
        @Override
        public boolean isSupport(RoundContext context, VariableElement fieldType) {
            return Arrays.asList(boolean.class.getName(), Boolean.class.getName()).contains(fieldType.asType().toString());
        }
        @Override
        void addColumnMethod(RoundContext context, CodeBlock.Builder codeBlock, String columnName) {
            codeBlock.add("._boolean($S)", columnName);
        }
    },DOUBLE(StringAttribute.class, "stringCol", "getDouble") {
        @Override
        public boolean isSupport(RoundContext context, VariableElement fieldType) {
            return Arrays.asList(float.class.getName(), Float.class.getName(), double.class.getName(), Double.class.getName())
                    .contains(fieldType.asType().toString());
        }

        @Override
        void addColumnMethod(RoundContext context, CodeBlock.Builder codeBlock, String columnName) {
            codeBlock.add("._decimal($S)", columnName);
        }
    },DATE(StringAttribute.class, "timeCol", "getDate") {
        @Override
        public boolean isSupport(RoundContext context, VariableElement fieldType) {
            return Arrays.asList(LocalDate.class.getName()).contains(fieldType.asType().toString());
        }
        @Override
        void addColumnMethod(RoundContext context, CodeBlock.Builder codeBlock, String columnName) {
            codeBlock.add("._datetime($S)", columnName);
        }
    },DATETIME(StringAttribute.class, "timeCol", "getTimestamp") {
        @Override
        public boolean isSupport(RoundContext context, VariableElement fieldType) {
            return Arrays.asList(LocalDateTime.class.getName()).contains(fieldType.asType().toString());
        }
        @Override
        void addColumnMethod(RoundContext context, CodeBlock.Builder codeBlock, String columnName) {
            codeBlock.add("._datetime($S)", columnName);
        }
    },ENUM(EnumAttribute.class, "stringCol", "getString") {
        @Override
        public boolean isSupport(RoundContext context, VariableElement fieldType) {
            ProcessingEnvironment processingEnv = context.getProcessingEnv();
            TypeMirror field = fieldType.asType();
            if (field.getKind() != TypeKind.DECLARED) {
                return false;
            }
            TypeMirror enumType = AnnotationProcessHelper.getTypeMirrorFromClass(context, Enum.class, field);
            return processingEnv.getTypeUtils().isAssignable(field, enumType);
        }

        @Override
        void addColumnMethod(RoundContext context, CodeBlock.Builder codeBlock, String columnName) {
            codeBlock.add("._string($S)", columnName);
        }
    };

    DatabaseColumnType(Class<? extends Attribute> attribute, String feelingTableMethod, String databaseValueMethodGetterName) {
        this.attribute = attribute;
        this.feelingTableMethod = feelingTableMethod;
        this.databaseValueMethodGetterName = databaseValueMethodGetterName;
    }

    private final Class<? extends Attribute> attribute;
    private final String feelingTableMethod;
    private final String databaseValueMethodGetterName;


    public abstract boolean isSupport(RoundContext context, VariableElement fieldType);

    abstract void addColumnMethod(RoundContext context, CodeBlock.Builder codeBlock, String columnName);

    public static Optional<DatabaseColumnType> findSupportedType(RoundContext context, VariableElement fieldType) {
        for (DatabaseColumnType columnType : values()) {
            if (columnType.isSupport(context, fieldType)) {
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

    public String getDatabaseValueMethodGetterName() {
        return databaseValueMethodGetterName;
    }
}
