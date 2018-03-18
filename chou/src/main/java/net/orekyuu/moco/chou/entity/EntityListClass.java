package net.orekyuu.moco.chou.entity;

import com.squareup.javapoet.*;
import net.orekyuu.moco.chou.CodeGenerateOperation;
import net.orekyuu.moco.feeling.Select;

import javax.annotation.Nonnull;
import javax.annotation.processing.Messager;
import javax.lang.model.element.Modifier;

public class EntityListClass {

    private final EntityClass entityClass;

    public EntityListClass(EntityClass entityClass) {
        this.entityClass = entityClass;
    }

    public MethodSpec constructor() {
        return MethodSpec.constructorBuilder()
                .addParameter(Select.class, "select")
                .addStatement("super(select)").build();
    }

    public MethodSpec getMapperMethod(TableClass tableClass) {
        return MethodSpec.methodBuilder("getMapper")
                .addAnnotation(Nonnull.class)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PUBLIC)
                .addStatement("return $T.$N", tableClass.getClassName(), tableClass.mapperField())
                .returns(ParameterizedTypeName.get(ClassName.get(Select.QueryResultMapper.class), entityClass.getClassName()))
                .build();
    }

    public ClassName getClassName() {
        return entityClass.getEntityListClassName();
    }

    public JavaFile createJavaFile(Messager messager, TableClass tableClass) {
        TypeSpec.Builder classBuilder = TypeSpec.classBuilder(getClassName())
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        ParameterizedTypeName superclass = ParameterizedTypeName.get(ClassName.get(net.orekyuu.moco.core.EntityList.class), getClassName(), entityClass.getClassName());
        classBuilder.superclass(superclass);
        CodeGenerateOperation.run(messager, () -> classBuilder.addMethod(constructor()));
        CodeGenerateOperation.run(messager, () -> classBuilder.addMethod(getMapperMethod(tableClass)));
        return JavaFile.builder(entityClass.getPackageElement().getQualifiedName().toString(), classBuilder.build())
                .build();
    }
}
