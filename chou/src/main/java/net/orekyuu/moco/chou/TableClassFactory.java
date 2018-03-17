package net.orekyuu.moco.chou;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.Nullable;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Modifier;

public class TableClassFactory {
    private OriginalEntity originalEntity;

    public TableClassFactory(OriginalEntity originalEntity) {
        this.originalEntity = originalEntity;
    }

    @Nullable
    public JavaFile createJavaFile(Messager messager) {
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(originalEntity.toTableClassName())
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        classBuilder.addField(TableClassFields.mapper(originalEntity));
        classBuilder.addField(TableClassFields.tableField(originalEntity));
        for (ColumnField field : originalEntity.getColumnFields()) {
            classBuilder.addField(TableClassFields.columnField(originalEntity, field));
        }
        classBuilder.addMethod(TableClassMethods.createMethod(originalEntity));
        return JavaFile.builder(originalEntity.getPackageElement().getQualifiedName().toString(), classBuilder.build())
                .build();
    }
}
