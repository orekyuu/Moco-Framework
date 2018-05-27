package net.orekyuu.moco.chou.attribute;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import net.orekyuu.moco.chou.NamingUtils;
import net.orekyuu.moco.chou.RoundContext;
import net.orekyuu.moco.chou.entity.EntityClass;
import net.orekyuu.moco.core.annotations.Column;
import net.orekyuu.moco.core.internal.TableClassHelper;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.VariableElement;

public abstract class AttributeField {
    final Column column;
    final VariableElement variableElement;
    final RoundContext context;

    public AttributeField(RoundContext context, Column column, VariableElement variableElement) {
        this.column = column;
        this.variableElement = variableElement;
        this.context = context;
    }

    public Column getColumn() {
        return column;
    }

    public VariableElement getVariableElement() {
        return variableElement;
    }

    public abstract ClassName getAttributeClass();

    public abstract ColumnFindableMethod getFeelingTableMethod();

    public String entityGetterMethod() {
        String name = variableElement.getSimpleName().toString();
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return "get" + name;
    }

    public boolean isUnique() {
        return column.unique();
    }

    public boolean isGeneratedValue() {
        return column.generatedValue();
    }

    public String tableClassColumnName() {
        String fieldName = column.variableName();
        if (fieldName.isEmpty()) {
            fieldName = NamingUtils.toUpperName(variableElement.getSimpleName().toString()).toUpperCase();
        }
        return fieldName;
    }

    protected ParameterizedTypeName tableClassFieldType(EntityClass entityClass) {
        return ParameterizedTypeName.get(getAttributeClass(), entityClass.getClassName());
    }

    public abstract CodeBlock createSetterBlock();

    public abstract CodeBlock createColumnMethod();

    public FieldSpec createTableClassField(EntityClass entityClass) {
        CodeBlock.Builder builder = CodeBlock.builder()
                .add("new $T<>(TABLE.$L($S), $T::$L)", getAttributeClass(), getFeelingTableMethod().getMethodName(), getColumn().name(), entityClass.getClassName(), entityGetterMethod());

        return FieldSpec.builder(
                tableClassFieldType(entityClass),
                tableClassColumnName(),
                Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                .initializer(builder.build())
                .build();
    }

    public CodeBlock createSqlBindParam() {
        return CodeBlock.builder().add("$T.createBindParam($L, entity)", TableClassHelper.class, tableClassColumnName()).build();
    }
}
