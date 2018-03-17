package net.orekyuu.moco.chou;

import com.squareup.javapoet.*;
import net.orekyuu.moco.core.ConnectionManager;
import net.orekyuu.moco.feeling.Insert;
import net.orekyuu.moco.feeling.node.SqlBindParam;
import net.orekyuu.moco.feeling.node.SqlNodeArray;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Collectors;

public class TableClassMethods {

    private TableClassMethods() {
        throw new UnsupportedOperationException();
    }

    public static MethodSpec createMethod(OriginalEntity entity) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("create")
                .returns(TypeName.VOID)
                .addParameter(
                        ParameterSpec.builder(TypeName.get(entity.getOriginalType().asType()), "entity")
                                .addAnnotation(AnnotationSpec.builder(Nonnull.class).build())
                                .build())
                .addModifiers(Modifier.STATIC, Modifier.PUBLIC)
                .addStatement("$T insert = new $T(TABLE)", Insert.class, Insert.class);

        String attrs = entity.getColumnFields().stream()
                .filter(f -> !f.isGeneratedValue())
                .map(ColumnField::tableClassColumnName)
                .map(str -> str + ".ast()")
                .collect(Collectors.joining(", "));
        builder.addStatement("insert.setAttributes(Arrays.asList($L))", attrs);

        CodeBlock.Builder codeBlock = CodeBlock.builder();
        codeBlock.add("insert.setValues(new $T($T.asList(", SqlNodeArray.class, Arrays.class);
        Iterator<ColumnField> columnFieldIterator = entity.getColumnFields().iterator();
        while (columnFieldIterator.hasNext()) {
            ColumnField columnField = columnFieldIterator.next();
            if (columnField.getColumn().generatedValue()) {
                continue;
            }
            codeBlock.add("new $T($L.getAccessor().get(entity), $L.bindType())", SqlBindParam.class, columnField.tableClassColumnName(), columnField.tableClassColumnName());
            if (columnFieldIterator.hasNext()) {
                codeBlock.add(", ");
            }
        }
        builder.addCode(codeBlock.add(")));\n").build());

        builder.addStatement("insert.executeQuery($T.getConnection())", ConnectionManager.class);
        return builder.build();
    }

//    public static UserList all() {
//        return new UserList(TABLE.select());
//    }
    public static MethodSpec allMethod(OriginalEntity entity) {
        return MethodSpec.methodBuilder("all")
                .addModifiers(Modifier.STATIC, Modifier.PUBLIC)
                .addAnnotation(Nonnull.class)
                .returns(entity.toEntityListClassName())
                .addStatement("return new $T(TABLE.select())", entity.toEntityListClassName())
                .build();
    }

    public static MethodSpec firstMethod(OriginalEntity entity) {
        return MethodSpec.methodBuilder("first")
                .addModifiers(Modifier.STATIC, Modifier.PUBLIC)
                .addAnnotation(Nonnull.class)
                .returns(ParameterizedTypeName.get(ClassName.get(Optional.class), entity.originalClassName()))
                // TODO: LIMIT作ったら直す
                .addStatement("return all().toList().stream().findFirst()", entity.toEntityListClassName())
                .build();
    }

    public static MethodSpec firstOrNullMethod(OriginalEntity entity) {
        return MethodSpec.methodBuilder("firstOrNull")
                .addModifiers(Modifier.STATIC, Modifier.PUBLIC)
                .addAnnotation(Nullable.class)
                .returns(entity.originalClassName())
                .addStatement("return first().orElse(null)", entity.toEntityListClassName())
                .build();
    }

    public static MethodSpec findMethod(OriginalEntity entity, ColumnField field) {
        VariableElement variableElement = field.getVariableElement();
        String fieldName = NamingUtils.toUpperFirst(variableElement.getSimpleName().toString());
        return MethodSpec.methodBuilder("findBy" + fieldName)
                .addModifiers(Modifier.STATIC, Modifier.PUBLIC)
                .addAnnotation(Nonnull.class)
                .returns(ParameterizedTypeName.get(ClassName.get(Optional.class), entity.originalClassName()))
                .addParameter(ParameterSpec.builder(ClassName.get(field.getVariableElement().asType()), "key").addAnnotation(Nonnull.class).build())
                .addStatement("return all().where($L.eq(key)).toList().stream().findFirst()", field.tableClassColumnName())
                .build();
    }

    public static MethodSpec findOrNullMethod(OriginalEntity entity, ColumnField field) {
        VariableElement variableElement = field.getVariableElement();
        String fieldName = NamingUtils.toUpperFirst(variableElement.getSimpleName().toString());
        return MethodSpec.methodBuilder("findOrNullBy" + fieldName)
                .addModifiers(Modifier.STATIC, Modifier.PUBLIC)
                .addAnnotation(Nullable.class)
                .returns(entity.originalClassName())
                .addParameter(ParameterSpec.builder(ClassName.get(field.getVariableElement().asType()), "key").addAnnotation(Nonnull.class).build())
                .addStatement("return all().where($L.eq(key)).toList().stream().findFirst().orElse(null)", field.tableClassColumnName())
                .build();
    }
}
