package net.orekyuu.moco.chou.attribute;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
import net.orekyuu.moco.chou.RoundContext;
import net.orekyuu.moco.chou.entity.EntityClass;
import net.orekyuu.moco.core.annotations.Column;
import net.orekyuu.moco.core.attribute.EnumAttribute;
import net.orekyuu.moco.feeling.node.SqlBindParam;

import javax.lang.model.element.VariableElement;

public class EnumAttributeField extends AttributeField {
    public EnumAttributeField(RoundContext context, Column column, VariableElement variableElement) {
        super(context, column, variableElement);
    }

    @Override
    public ClassName getAttributeClass() {
        return ClassName.get(EnumAttribute.class);
    }

    @Override
    public String getFeelingTableMethod() {
        return "stringCol";
    }

    @Override
    protected ParameterizedTypeName tableClassFieldType(EntityClass entityClass) {
        VariableElement variableElement = getVariableElement();
        return ParameterizedTypeName.get(getAttributeClass(), entityClass.getClassName(), ClassName.get(variableElement.asType()));
    }

    @Override
    public CodeBlock createSetterBlock() {
        VariableElement element = getVariableElement();
        return CodeBlock.builder().addStatement("$L.set(record, $T.valueOf($T.class, resultSet.getString($S)))", element.getSimpleName().toString(), Enum.class, ClassName.get(element.asType()), getColumn().name()).build();
    }

    @Override
    public CodeBlock createColumnMethod() {
        return CodeBlock.builder().add("._string($S)", column.name()).build();
    }

    @Override
    public CodeBlock createSqlBindParam() {
        return CodeBlock.builder().add("new $T((($T)($L.getAccessor().get(entity))).name(), $L.bindType())", SqlBindParam.class, Enum.class, tableClassColumnName(), tableClassColumnName()).build();
    }
}
