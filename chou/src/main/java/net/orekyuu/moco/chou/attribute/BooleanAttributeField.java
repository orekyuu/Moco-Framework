package net.orekyuu.moco.chou.attribute;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import net.orekyuu.moco.chou.RoundContext;
import net.orekyuu.moco.core.annotations.Column;
import net.orekyuu.moco.core.attribute.BooleanAttribute;

import javax.lang.model.element.VariableElement;

public class BooleanAttributeField extends AttributeField {
    public BooleanAttributeField(RoundContext context, Column column, VariableElement variableElement) {
        super(context, column, variableElement);
    }

    @Override
    public ClassName getAttributeClass() {
        return ClassName.get(BooleanAttribute.class);
    }

    @Override
    public String getFeelingTableMethod() {
        return "booleanCol";
    }

    @Override
    public String entityGetterMethod() {
        if (!variableElement.asType().toString().equals("boolean")) {
            return super.entityGetterMethod();
        }

        String name = variableElement.getSimpleName().toString();
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        return "is" + name;
    }

    @Override
    public CodeBlock createSetterBlock() {
        return CodeBlock.builder().addStatement("$L.set(record, resultSet.getBoolean($S))", variableElement.getSimpleName().toString(), column.name()).build();
    }
}
