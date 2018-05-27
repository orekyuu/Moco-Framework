package net.orekyuu.moco.chou.entity;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import net.orekyuu.moco.chou.CompilerException;
import net.orekyuu.moco.chou.NamingUtils;
import net.orekyuu.moco.chou.RoundContext;
import net.orekyuu.moco.chou.attribute.AttributeField;

import javax.annotation.Nullable;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import java.util.List;
import java.util.Optional;

public abstract class RelationField {
    private final VariableElement fieldElement;
    protected final RoundContext context;

    public RelationField(RoundContext context, VariableElement fieldElement) {
        this.context = context;
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
        if (!isSupportedClass(fieldType)) {
            return null;
        }

        return toChildClassElement(fieldType);
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
