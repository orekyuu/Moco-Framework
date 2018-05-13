package net.orekyuu.moco.chou.entity;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import net.orekyuu.moco.chou.AttributeField;
import net.orekyuu.moco.chou.CompilerException;
import net.orekyuu.moco.chou.NamingUtils;
import net.orekyuu.moco.chou.RoundContext;
import net.orekyuu.moco.chou.visitor.ColumnFieldVisitor;
import net.orekyuu.moco.core.ReflectUtil;
import net.orekyuu.moco.core.annotations.HasOne;
import net.orekyuu.moco.core.relation.HasOneRelation;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;

public class HasOneRelationField extends RelationField {
    private final HasOne hasOne;
    public HasOneRelationField(RoundContext context, VariableElement fieldElement, HasOne hasOne) {
        super(context, fieldElement);
        this.hasOne = hasOne;
    }

    @Override
    boolean isSupportedClass(DeclaredType fieldType) {
        return true;
    }

    @Override
    TypeElement toChildClassElement(DeclaredType fieldType) {
        return ((TypeElement) fieldType.asElement());
    }

    @Override
    FieldSpec createFieldSpec(EntityClass entityClass, TableClass tableClass, TypeElement childEntityTypeElement) {
        ClassName childClassName = ClassName.get(childEntityTypeElement);
        ClassName childTableClassName = childTableClassName(childClassName);

        ColumnFieldVisitor parentVisitor = new ColumnFieldVisitor(context);
        parentVisitor.scan(entityClass.getEntityType());
        AttributeField parentAttribute = findByName(parentVisitor.getAttrs(), hasOne.key())
                .orElseThrow(() -> new CompilerException(getFieldElement(), hasOne.key() + "はentityClass " + entityClass.getClassName().toString() + "に定義されていません"));

        ColumnFieldVisitor childVisitor = new ColumnFieldVisitor(context);
        childVisitor.scan(childEntityTypeElement);
        AttributeField childAttribute = findByName(childVisitor.getAttrs(), hasOne.foreignKey())
                .orElseThrow(() -> new CompilerException(getFieldElement(), hasOne.foreignKey() + "はentityClass " + childClassName.toString() + "に定義されていません"));

        FieldSpec.Builder builder = FieldSpec.builder(ParameterizedTypeName.get(ClassName.get(HasOneRelation.class), entityClass.getClassName(), childClassName),
                getFieldName(entityClass, childClassName))
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer("new $T($N, $L, $T.TABLE, $T.$L, $T.MAPPER, $T.getFieldSetter($T.class, $S))",
                        ParameterizedTypeName.get(ClassName.get(HasOneRelation.class), entityClass.getClassName(), childClassName), tableClass.tableField(context), parentAttribute.tableClassColumnName(),
                        childTableClassName, childTableClassName, childAttribute.tableClassColumnName(), childTableClassName, ReflectUtil.class, entityClass.getClassName(), getFieldElement().getSimpleName());
        return builder.build();
    }

    private String getFieldName(EntityClass entityClass, ClassName childClassName) {
        if (!hasOne.variableName().isEmpty()) {
            return hasOne.variableName();
        }
        return NamingUtils.toUpperName(entityClass.getClassName().simpleName()) + "_TO_" + NamingUtils.toUpperName(childClassName.simpleName());
    }
}
