package net.orekyuu.moco.chou.attribute;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import net.orekyuu.moco.chou.RoundContext;
import net.orekyuu.moco.core.annotations.Column;
import net.orekyuu.moco.core.attribute.IntAttribute;
import net.orekyuu.moco.core.attribute.LongAttribute;

import javax.lang.model.element.VariableElement;

public class LongAttributeField extends AttributeField {
    public LongAttributeField(RoundContext context, Column column, VariableElement variableElement) {
        super(context, column, variableElement);
    }

    @Override
    public ClassName getAttributeClass() {
        return ClassName.get(LongAttribute.class);
    }

    @Override
    public ColumnFindableMethod getFeelingTableMethod() {
        return ColumnFindableMethod.LONG;
    }

    @Override
    public CodeBlock createSetterBlock() {
        return CodeBlock.builder().addStatement("$L.set(record, resultSet.getLong($S))", variableElement.getSimpleName().toString(), column.name()).build();
    }

    @Override
    public CodeBlock createColumnMethod() {
        return CodeBlock.builder().add("._long($S)", column.name()).build();
    }
}
