package net.orekyuu.moco.chou.entity;

import com.squareup.javapoet.*;
import net.orekyuu.moco.chou.AttributeField;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Modifier;
import javax.lang.model.util.Types;

import static net.orekyuu.moco.chou.CodeGenerateOperation.run;

public class TableClass {

    private final EntityClass entityClass;

    public TableClass(EntityClass entityClass) {
        this.entityClass = entityClass;
    }

    public MethodSpec createMethod() {
        return TableClassMethods.createMethod(entityClass);
    }

    public MethodSpec findMethod(AttributeField attributeField) {
        return TableClassMethods.findMethod(entityClass, attributeField);
    }

    private MethodSpec findOrNullMethod(AttributeField field) {
        return TableClassMethods.findOrNullMethod(entityClass, field);
    }

    public MethodSpec allMethod() {
        return TableClassMethods.allMethod(entityClass);
    }

    public MethodSpec firstMethod() {
        return TableClassMethods.firstMethod(entityClass);
    }

    public MethodSpec firstOrNullMethod() {
        return TableClassMethods.firstOrNullMethod(entityClass);
    }

    public FieldSpec mapperField() {
        return TableClassFields.mapper(entityClass);
    }

    public FieldSpec tableField() {
        return TableClassFields.tableField(entityClass);
    }

    public FieldSpec attributeField(AttributeField attributeField) {
        return TableClassFields.columnField(entityClass, attributeField);
    }

    public MethodSpec hasManyField(HasManyRelationField hasManyRelationField, TableClass tableClass) {
        return hasManyRelationField.relationField(tableClass, entityClass);
    }

    public ClassName getClassName() {
        return entityClass.getTableClassName();
    }

    public JavaFile createJavaFile(Messager messager) {
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(getClassName())
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        run(messager, () -> classBuilder.addField(mapperField()));
        run(messager, () -> classBuilder.addField(tableField()));
        for (AttributeField field : entityClass.getAttributeFields()) {
            run(messager, () -> classBuilder.addField(attributeField(field)));
        }
        for (HasManyRelationField field : entityClass.getHasManyRelationFields()) {
            run(messager, () -> classBuilder.addMethod(hasManyField(field, this)));
        }

        run(messager, () -> classBuilder.addMethod(createMethod()));
        run(messager, () -> classBuilder.addMethod(allMethod()));
        run(messager, () -> classBuilder.addMethod(firstMethod()));
        run(messager, () -> classBuilder.addMethod(firstOrNullMethod()));
        for (AttributeField field : entityClass.getAttributeFields()) {
            if (field.isUnique()) {
                run(messager, () -> classBuilder.addMethod(findMethod(field)));
                run(messager, () -> classBuilder.addMethod(findOrNullMethod(field)));
            }
        }

        return JavaFile.builder(entityClass.getPackageElement().getQualifiedName().toString(), classBuilder.build())
                .build();
    }
}
