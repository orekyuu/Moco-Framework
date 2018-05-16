package net.orekyuu.moco.chou.entity;

import com.squareup.javapoet.*;
import net.orekyuu.moco.chou.NamingUtils;
import net.orekyuu.moco.chou.attribute.AttributeField;
import net.orekyuu.moco.core.ConnectionManager;
import net.orekyuu.moco.feeling.Insert;
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

    public static MethodSpec createMethod(EntityClass entity) {
        MethodSpec.Builder builder = MethodSpec.methodBuilder("create")
                .returns(TypeName.VOID)
                .addParameter(
                        ParameterSpec.builder(TypeName.get(entity.getEntityType().asType()), "entity")
                                .addAnnotation(AnnotationSpec.builder(Nonnull.class).build())
                                .build())
                .addModifiers(Modifier.STATIC, Modifier.PUBLIC)
                .addStatement("$T insert = new $T(TABLE)", Insert.class, Insert.class);

        String attrs = entity.getAttributeFields().stream()
                .filter(f -> !f.isGeneratedValue())
                .map(AttributeField::tableClassColumnName)
                .map(str -> str + ".ast()")
                .collect(Collectors.joining(", "));
        builder.addStatement("insert.setAttributes(Arrays.asList($L))", attrs);

        CodeBlock.Builder codeBlock = CodeBlock.builder();
        codeBlock.add("insert.setValues(new $T($T.asList(", SqlNodeArray.class, Arrays.class);
        Iterator<AttributeField> columnFieldIterator = entity.getAttributeFields().iterator();
        while (columnFieldIterator.hasNext()) {
            AttributeField attributeField = columnFieldIterator.next();
            if (attributeField.isGeneratedValue()) {
                continue;
            }

            codeBlock.add(attributeField.createSqlBindParam());

            if (columnFieldIterator.hasNext()) {
                codeBlock.add(", ");
            }
        }
        builder.addCode(codeBlock.add(")));\n").build());

        builder.addStatement("insert.executeQuery($T.getConnection())", ConnectionManager.class);
        return builder.build();
    }

    public static MethodSpec allMethod(EntityClass entity) {
        return MethodSpec.methodBuilder("all")
                .addModifiers(Modifier.STATIC, Modifier.PUBLIC)
                .addAnnotation(Nonnull.class)
                .returns(entity.getEntityListClassName())
                .addStatement("return new $T(TABLE)", entity.getEntityListClassName())
                .build();
    }

    public static MethodSpec firstMethod(EntityClass entity) {
        return MethodSpec.methodBuilder("first")
                .addModifiers(Modifier.STATIC, Modifier.PUBLIC)
                .addAnnotation(Nonnull.class)
                .returns(ParameterizedTypeName.get(ClassName.get(Optional.class), entity.getClassName()))
                .addStatement("return all().limit(1).toList().stream().findFirst()", entity.getEntityListClassName())
                .build();
    }

    public static MethodSpec firstOrNullMethod(EntityClass entity) {
        return MethodSpec.methodBuilder("firstOrNull")
                .addModifiers(Modifier.STATIC, Modifier.PUBLIC)
                .addAnnotation(Nullable.class)
                .returns(entity.getClassName())
                .addStatement("return first().orElse(null)", entity.getEntityListClassName())
                .build();
    }

    public static MethodSpec findMethod(EntityClass entity, AttributeField field) {
        VariableElement variableElement = field.getVariableElement();
        String fieldName = NamingUtils.toUpperFirst(variableElement.getSimpleName().toString());
        return MethodSpec.methodBuilder("findBy" + fieldName)
                .addModifiers(Modifier.STATIC, Modifier.PUBLIC)
                .addAnnotation(Nonnull.class)
                .returns(ParameterizedTypeName.get(ClassName.get(Optional.class), entity.getClassName()))
                .addParameter(ParameterSpec.builder(ClassName.get(field.getVariableElement().asType()), "key").addAnnotation(Nonnull.class).build())
                .addStatement("return all().where($L.eq(key)).limit(1).toList().stream().findFirst()", field.tableClassColumnName())
                .build();
    }

    public static MethodSpec findOrNullMethod(EntityClass entity, AttributeField field) {
        VariableElement variableElement = field.getVariableElement();
        String fieldName = NamingUtils.toUpperFirst(variableElement.getSimpleName().toString());
        return MethodSpec.methodBuilder("findOrNullBy" + fieldName)
                .addModifiers(Modifier.STATIC, Modifier.PUBLIC)
                .addAnnotation(Nullable.class)
                .returns(entity.getClassName())
                .addParameter(ParameterSpec.builder(ClassName.get(field.getVariableElement().asType()), "key").addAnnotation(Nonnull.class).build())
                .addStatement("return all().where($L.eq(key)).limit(1).toList().stream().findFirst().orElse(null)", field.tableClassColumnName())
                .build();
    }
}
