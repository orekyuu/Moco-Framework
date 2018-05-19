package net.orekyuu.moco.chou.attribute;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import net.orekyuu.moco.chou.RoundContext;
import net.orekyuu.moco.core.annotations.Column;
import net.orekyuu.moco.core.attribute.BigDecimalAttribute;

import javax.lang.model.element.VariableElement;

public class BigDecimalAttributeField extends AttributeField {
    public BigDecimalAttributeField(RoundContext context, Column column, VariableElement variableElement) {
        super(context, column, variableElement);
    }

    @Override
    public ClassName getAttributeClass() {
        return ClassName.get(BigDecimalAttribute.class);
    }

    @Override
    public ColumnFindableMethod getFeelingTableMethod() {
        return ColumnFindableMethod.BIG_DECIMAL;
    }

    @Override
    public CodeBlock createSetterBlock() {
        return CodeBlock.builder().addStatement("$L.set(record, resultSet.getBigDecimal($S))", variableElement.getSimpleName().toString(), column.name()).build();
    }

    @Override
    public CodeBlock createColumnMethod() {
        return CodeBlock.builder().add("._decimal($S)", column.name()).build();
    }
}
