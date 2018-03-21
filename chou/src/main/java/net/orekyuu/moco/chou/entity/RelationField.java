package net.orekyuu.moco.chou.entity;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import net.orekyuu.moco.chou.AttributeField;
import net.orekyuu.moco.chou.CompilerException;
import net.orekyuu.moco.chou.NamingUtils;

import javax.annotation.Nullable;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

public abstract class RelationField {
    private VariableElement fieldElement;

    public RelationField(VariableElement fieldElement) {
        this.fieldElement = fieldElement;
    }

    public VariableElement getFieldElement() {
        return fieldElement;
    }

    abstract boolean isSupportedClass(DeclaredType fieldType);

    abstract TypeElement toChildClassElement(DeclaredType fieldType);

    abstract FieldSpec createFieldSpec(EntityClass entityClass, TableClass tableClass, TypeElement childEntityTypeElement);

    public FieldSpec createFieldSpec(EntityClass entityClass, TableClass tableClass) {
        TypeElement childClassElement = getChildClassElement();
        if (childClassElement == null) {
            throw new CompilerException(getFieldElement(), getFieldElement().getSimpleName() + "はサポートされていない型で宣言されています。");
        }

        return createFieldSpec(entityClass, tableClass, childClassElement);
    }

    @Nullable
    public TypeElement getChildClassElement() {
        if (!(fieldElement.asType() instanceof DeclaredType)) {
            return null;
        }
        DeclaredType fieldType = ((DeclaredType) fieldElement.asType());
        if (!isSupportedClass(fieldType)) {
            return null;
        }
        ClassName rawFieldType = ((ParameterizedTypeName) ClassName.get(fieldType)).rawType;
        if (Stream.of(List.class, Set.class).noneMatch(it -> ClassName.get(it).equals(rawFieldType))) {
            return null;
        }

        List<? extends TypeMirror> typeArguments = fieldType.getTypeArguments();
        if (typeArguments.size() != 1) {
            return null;
        }
        TypeMirror typeMirror = typeArguments.get(0);
        if (!(typeMirror instanceof DeclaredType)) {
            return null;
        }
        DeclaredType childType = (DeclaredType) typeMirror;
        return (TypeElement) childType.asElement();
    }

    ClassName childTableClassName(ClassName childEntityClass) {
        String packageName = childEntityClass.packageName();
        String simpleName = NamingUtils.multipleName(childEntityClass.simpleName());
        return ClassName.get(packageName, simpleName);
    }

    Optional<AttributeField> findByName(List<AttributeField> attrs, String columnName) {
        return attrs.stream().filter(attr -> attr.getColumn().name().equals(columnName)).findFirst();
    }
}
