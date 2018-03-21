package net.orekyuu.moco.chou.entity;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import net.orekyuu.moco.chou.AttributeField;
import net.orekyuu.moco.chou.CompilerException;
import net.orekyuu.moco.chou.NamingUtils;
import net.orekyuu.moco.chou.visitor.ColumnFieldVisitor;
import net.orekyuu.moco.core.ReflectUtil;
import net.orekyuu.moco.core.annotations.HasMany;
import net.orekyuu.moco.core.relation.HasManyRelation;

import javax.annotation.Nonnull;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class HasManyRelationField extends RelationField {
    private final HasMany hasMany;

    public HasManyRelationField(VariableElement field, HasMany hasMany) {
        super(field);
        this.hasMany = hasMany;
    }

    @Override
    boolean isSupportedClass(DeclaredType fieldType) {
        ClassName rawFieldType = ((ParameterizedTypeName) ClassName.get(fieldType)).rawType;
        if (Stream.of(List.class, Set.class).noneMatch(it -> ClassName.get(it).equals(rawFieldType))) {
            return false;
        }

        List<? extends TypeMirror> typeArguments = fieldType.getTypeArguments();
        if (typeArguments.size() != 1) {
            return false;
        }
        TypeMirror typeMirror = typeArguments.get(0);
        return typeMirror instanceof DeclaredType;
    }

    @Override
    TypeElement toChildClassElement(DeclaredType fieldType) {
        return (TypeElement) ((DeclaredType) fieldType.getTypeArguments().get(0)).asElement();
    }

    @Override
    FieldSpec createFieldSpec(EntityClass entityClass, TableClass tableClass, TypeElement childEntityTypeElement) {
        ClassName childClassName = ClassName.get(childEntityTypeElement);
        ClassName childTableClassName = childTableClassName(childClassName);

        ColumnFieldVisitor parentVisitor = new ColumnFieldVisitor();
        parentVisitor.scan(entityClass.getEntityType());
        AttributeField parentAttribute = findByName(parentVisitor.getAttrs(), hasMany.key())
                .orElseThrow(() -> new CompilerException(getFieldElement(), hasMany.key() + "はentityClass " + entityClass.getClassName().toString() + "に定義されていません"));

        ColumnFieldVisitor childVisitor = new ColumnFieldVisitor();
        childVisitor.scan(childEntityTypeElement);
        AttributeField childAttribute = findByName(childVisitor.getAttrs(), hasMany.foreignKey())
                .orElseThrow(() -> new CompilerException(getFieldElement(), hasMany.foreignKey() + "はentityClass " + childClassName.toString() + "に定義されていません"));


        FieldSpec.Builder builder = FieldSpec.builder(ParameterizedTypeName.get(ClassName.get(HasManyRelation.class), entityClass.getClassName(), childClassName),
                NamingUtils.toUpperName(entityClass.getClassName().simpleName()) + "_TO_" + NamingUtils.toUpperName(NamingUtils.multipleName(childClassName.simpleName())))
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .addAnnotation(Nonnull.class)
                .initializer("new $T($N, $L, $T.TABLE, $T.$L, $T.MAPPER, $T.getFieldSetter($T.class, $S))",
                        ParameterizedTypeName.get(ClassName.get(HasManyRelation.class), entityClass.getClassName(), childClassName), tableClass.tableField(), parentAttribute.tableClassColumnName(),
                        childTableClassName, childTableClassName, childAttribute.tableClassColumnName(), childTableClassName, ReflectUtil.class, entityClass.getClassName(), getFieldElement().getSimpleName());
        return builder.build();
    }

}
