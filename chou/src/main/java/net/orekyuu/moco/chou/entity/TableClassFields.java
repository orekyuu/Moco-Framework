package net.orekyuu.moco.chou.entity;

import com.squareup.javapoet.*;
import net.orekyuu.moco.chou.RoundContext;
import net.orekyuu.moco.chou.attribute.AttributeField;
import net.orekyuu.moco.core.annotations.Column;
import net.orekyuu.moco.core.internal.TableClassHelper;
import net.orekyuu.moco.feeling.Select;
import net.orekyuu.moco.feeling.Table;
import net.orekyuu.moco.feeling.TableBuilder;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TableClassFields {
    private TableClassFields() {
        throw new UnsupportedOperationException();
    }

    public static FieldSpec mapper(EntityClass entityClass) {
        ParameterizedTypeName mapperType = ParameterizedTypeName.get(ClassName.get(Select.QueryResultMapper.class), entityClass.getClassName());


        TypeSpec.Builder mapperClass = TypeSpec.anonymousClassBuilder("")
                .addSuperinterface(mapperType);

        MethodSpec.Builder mappingMethod = MethodSpec.methodBuilder("mapping")
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .returns(entityClass.getClassName())
                .addParameter(ParameterSpec.builder(ClassName.get(ResultSet.class), "resultSet").build())
                .addException(ClassName.get(SQLException.class))
                .addException(ClassName.get(ReflectiveOperationException.class));

        mappingMethod.addStatement("$T record = new $T()", entityClass.getEntityType(), entityClass.getEntityType());
        CodeBlock.Builder initializer = CodeBlock.builder();
        initializer.beginControlFlow("try");
        for (AttributeField attributeField : entityClass.getAttributeFields()) {
            Column column = attributeField.getColumn();
            VariableElement variableElement = attributeField.getVariableElement();
            String fieldName = variableElement.getSimpleName().toString();
            String columnName = column.name();

            // add field
            mapperClass.addField(FieldSpec.builder(ClassName.get(Field.class), fieldName).addModifiers(Modifier.PRIVATE).build());
            // initialize field
            initializer.addStatement("$L = $T.getDeclaredField($T.class, $S)", fieldName, TableClassHelper.class, entityClass.getClassName(), fieldName);

            mappingMethod.addCode(attributeField.createSetterBlock());
        }
        mappingMethod.addStatement("return record");
        // end constructor
        initializer.nextControlFlow("catch ($T e)", ReflectiveOperationException.class)
                .addStatement("throw new $T(e)", RuntimeException.class)
                .endControlFlow();

        mapperClass.addInitializerBlock(initializer.build());
        mapperClass.addMethod(mappingMethod.build());

        TypeSpec anonymous = mapperClass.build();

        return FieldSpec.builder(mapperType, "MAPPER", Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer("$L", anonymous)
                .build();
    }

    public static FieldSpec tableField(RoundContext roundContext, EntityClass entityClass) {
        CodeBlock.Builder block = CodeBlock.builder().add("new $T($S, MAPPER)", TableBuilder.class, entityClass.getTable().name());
        for (AttributeField field : entityClass.getAttributeFields()) {
            CodeBlock codeBlock = field.createColumnMethod();
            block.add(codeBlock);
        }
        block.add(".build()");
        return FieldSpec.builder(Table.class, "TABLE", Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer(block.build())
                .build();
    }

    public static FieldSpec columnField(EntityClass entityClass, AttributeField field) {
        return field.createTableClassField(entityClass);
    }
}
