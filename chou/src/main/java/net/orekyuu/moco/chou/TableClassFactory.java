package net.orekyuu.moco.chou;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.Nullable;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Modifier;

import static net.orekyuu.moco.chou.CodeGenerateOperation.*;

public class TableClassFactory {
    private OriginalEntity originalEntity;

    public TableClassFactory(OriginalEntity originalEntity) {
        this.originalEntity = originalEntity;
    }

    @Nullable
    public JavaFile createJavaFile(Messager messager) {
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(originalEntity.toTableClassName())
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        run(messager, () -> classBuilder.addField(TableClassFields.mapper(originalEntity)));
        run(messager, () -> classBuilder.addField(TableClassFields.tableField(originalEntity)));
        for (ColumnField field : originalEntity.getColumnFields()) {
            run(messager, () -> classBuilder.addField(TableClassFields.columnField(originalEntity, field)));
        }
        run(messager, () -> classBuilder.addMethod(TableClassMethods.createMethod(originalEntity)));
        run(messager, () -> classBuilder.addMethod(TableClassMethods.allMethod(originalEntity)));
        run(messager, () -> classBuilder.addMethod(TableClassMethods.firstMethod(originalEntity)));
        run(messager, () -> classBuilder.addMethod(TableClassMethods.firstOrNullMethod(originalEntity)));
        for (ColumnField field : originalEntity.getColumnFields()) {
            if (field.isUnique()) {
                run(messager, () -> classBuilder.addMethod(TableClassMethods.findMethod(originalEntity, field)));
                run(messager, () -> classBuilder.addMethod(TableClassMethods.findOrNullMethod(originalEntity, field)));
            }
        }
        return JavaFile.builder(originalEntity.getPackageElement().getQualifiedName().toString(), classBuilder.build())
                .build();
    }
}
