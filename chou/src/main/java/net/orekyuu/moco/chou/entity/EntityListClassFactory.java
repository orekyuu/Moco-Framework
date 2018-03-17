package net.orekyuu.moco.chou.entity;

import com.squareup.javapoet.*;
import net.orekyuu.moco.core.EntityList;
import net.orekyuu.moco.feeling.Select;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Modifier;

public class EntityListClassFactory {

    private final EntityClass entityClass;

    public EntityListClassFactory(EntityClass entityClass) {
        this.entityClass = entityClass;
    }

    @Nullable
    public JavaFile createJavaFile(Messager messager) {
        ClassName listClassName = entityClass.getEntityListClassName();
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(listClassName)
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        ParameterizedTypeName superclass = ParameterizedTypeName.get(ClassName.get(EntityList.class), listClassName, entityClass.entityClassName());
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
                        .addStatement("return $T.MAPPER", entityClass.getTableClassName())
                        .returns(ParameterizedTypeName.get(ClassName.get(Select.QueryResultMapper.class), entityClass.entityClassName()))
                        .build());

        return JavaFile.builder(entityClass.getPackageElement().getQualifiedName().toString(), classBuilder.build())
                .build();
    }
}
