package net.orekyuu.moco.chou.entity;

import com.squareup.javapoet.*;
import net.orekyuu.moco.chou.RoundContext;
import net.orekyuu.moco.chou.attribute.AttributeField;

import javax.lang.model.element.Modifier;

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

    public FieldSpec tableField(RoundContext context) {
        return TableClassFields.tableField(context, entityClass);
    }

    public FieldSpec attributeField(AttributeField attributeField) {
        return TableClassFields.columnField(entityClass, attributeField);
    }

    public FieldSpec relationField(RelationField relationField, TableClass tableClass) {
        return relationField.createFieldSpec(entityClass, tableClass);
    }

    public ClassName getClassName() {
        return entityClass.getTableClassName();
    }

    public JavaFile createJavaFile(RoundContext roundContext) {
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(getClassName())
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        run(roundContext, () -> classBuilder.addField(mapperField()));
        run(roundContext, () -> classBuilder.addField(tableField(roundContext)));
        for (AttributeField field : entityClass.getAttributeFields()) {
            run(roundContext, () -> classBuilder.addField(attributeField(field)));
        }
        for (RelationField field : entityClass.getRelationFields()) {
            run(roundContext, () -> classBuilder.addField(relationField(field, this)));
        }

        run(roundContext, () -> classBuilder.addMethod(createMethod()));
        run(roundContext, () -> classBuilder.addMethod(allMethod()));
        run(roundContext, () -> classBuilder.addMethod(firstMethod()));
        run(roundContext, () -> classBuilder.addMethod(firstOrNullMethod()));
        for (AttributeField field : entityClass.getAttributeFields()) {
            if (field.isUnique()) {
                run(roundContext, () -> classBuilder.addMethod(findMethod(field)));
                run(roundContext, () -> classBuilder.addMethod(findOrNullMethod(field)));
            }
        }

        return JavaFile.builder(entityClass.getPackageElement().getQualifiedName().toString(), classBuilder.build())
                .build();
    }
}
