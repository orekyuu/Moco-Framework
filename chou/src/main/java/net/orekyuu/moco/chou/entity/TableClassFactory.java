package net.orekyuu.moco.chou.entity;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import net.orekyuu.moco.chou.AttributeField;

import javax.annotation.Nullable;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Modifier;

import static net.orekyuu.moco.chou.CodeGenerateOperation.*;

public class TableClassFactory {
    private EntityClass entityClass;

    public TableClassFactory(EntityClass entityClass) {
        this.entityClass = entityClass;
    }

    @Nullable
    public JavaFile createJavaFile(Messager messager) {
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(entityClass.getTableClassName())
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        run(messager, () -> classBuilder.addField(TableClassFields.mapper(entityClass)));
        run(messager, () -> classBuilder.addField(TableClassFields.tableField(entityClass)));
        for (AttributeField field : entityClass.getAttributeFields()) {
            run(messager, () -> classBuilder.addField(TableClassFields.columnField(entityClass, field)));
        }
        run(messager, () -> classBuilder.addMethod(TableClassMethods.createMethod(entityClass)));
        run(messager, () -> classBuilder.addMethod(TableClassMethods.allMethod(entityClass)));
        run(messager, () -> classBuilder.addMethod(TableClassMethods.firstMethod(entityClass)));
        run(messager, () -> classBuilder.addMethod(TableClassMethods.firstOrNullMethod(entityClass)));
        for (AttributeField field : entityClass.getAttributeFields()) {
            if (field.isUnique()) {
                run(messager, () -> classBuilder.addMethod(TableClassMethods.findMethod(entityClass, field)));
                run(messager, () -> classBuilder.addMethod(TableClassMethods.findOrNullMethod(entityClass, field)));
            }
        }
        return JavaFile.builder(entityClass.getPackageElement().getQualifiedName().toString(), classBuilder.build())
                .build();
    }
}
