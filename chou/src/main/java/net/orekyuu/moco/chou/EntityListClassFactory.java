package net.orekyuu.moco.chou;

import com.squareup.javapoet.*;
import net.orekyuu.moco.core.EntityList;
import net.orekyuu.moco.feeling.Select;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Modifier;

public class EntityListClassFactory {

    private final OriginalEntity originalEntity;

    public EntityListClassFactory(OriginalEntity originalEntity) {
        this.originalEntity = originalEntity;
    }

    @Nullable
    public JavaFile createJavaFile(Messager messager) {
        ClassName listClassName = originalEntity.toEntityListClassName();
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(listClassName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        ParameterizedTypeName superclass = ParameterizedTypeName.get(ClassName.get(EntityList.class), listClassName, originalEntity.originalClassName());
        classBuilder.superclass(superclass);
        classBuilder
                .addMethod(MethodSpec.constructorBuilder()
                        .addParameter(Select.class, "select")
                        .addStatement("super(select)").build());
        classBuilder.addMethod(
                MethodSpec.methodBuilder("getMapper")
                        .addAnnotation(Nonnull.class)
                        .addAnnotation(Override.class)
                        .addModifiers(Modifier.PUBLIC)
                        .addStatement("return $T.MAPPER", originalEntity.toTableClassName())
                        .returns(ParameterizedTypeName.get(ClassName.get(Select.QueryResultMapper.class), originalEntity.originalClassName()))
                        .build());

        return JavaFile.builder(originalEntity.getPackageElement().getQualifiedName().toString(), classBuilder.build())
                .build();
    }
}
