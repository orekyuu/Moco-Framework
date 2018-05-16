package net.orekyuu.moco.chou.attribute;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import net.orekyuu.moco.chou.RoundContext;
import net.orekyuu.moco.core.annotations.Column;
import net.orekyuu.moco.core.attribute.LocalDateTimeAttribute;

import javax.lang.model.element.VariableElement;
import java.util.Optional;

public class LocalDateTimeAttributeField extends AttributeField {
    public LocalDateTimeAttributeField(RoundContext context, Column column, VariableElement variableElement) {
        super(context, column, variableElement);
    }

    @Override
    public ClassName getAttributeClass() {
        return ClassName.get(LocalDateTimeAttribute.class);
    }

    @Override
    public String getFeelingTableMethod() {
        return "timeCol";
    }

    @Override
    public CodeBlock createSetterBlock() {
        return CodeBlock.builder().addStatement("$L.set(record, $T.ofNullable(resultSet.getTimestamp($S)).map(t -> t.toLocalDateTime()).orElse(null))",
                variableElement.getSimpleName().toString(), Optional.class, column.name()).build();
    }

    @Override
    public CodeBlock createColumnMethod() {
        return CodeBlock.builder().add("._datetime($S)", column.name()).build();
    }
}
