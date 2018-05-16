package net.orekyuu.moco.chou.attribute;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import net.orekyuu.moco.chou.RoundContext;
import net.orekyuu.moco.core.annotations.Column;
import net.orekyuu.moco.core.attribute.IntAttribute;

import javax.lang.model.element.VariableElement;

public class IntAttributeField extends AttributeField {
    public IntAttributeField(RoundContext context, Column column, VariableElement variableElement) {
        super(context, column, variableElement);
    }

    @Override
    public ClassName getAttributeClass() {
        return ClassName.get(IntAttribute.class);
    }

    @Override
    public String getFeelingTableMethod() {
        return "intCol";
    }

    @Override
    public CodeBlock createSetterBlock() {
        return CodeBlock.builder().addStatement("$L.set(record, resultSet.getInt($S))", variableElement.getSimpleName().toString(), column.name()).build();
    }

    @Override
    public CodeBlock createColumnMethod() {
        return CodeBlock.builder().add("._integer($S)", column.name()).build();
    }
}
