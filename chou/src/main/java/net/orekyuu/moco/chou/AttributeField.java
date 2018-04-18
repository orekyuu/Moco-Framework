package net.orekyuu.moco.chou;

import com.squareup.javapoet.ClassName;
import net.orekyuu.moco.core.annotations.Column;
import net.orekyuu.moco.core.attribute.BooleanAttribute;
import net.orekyuu.moco.core.attribute.IntAttribute;
import net.orekyuu.moco.core.attribute.StringAttribute;
import net.orekyuu.moco.feeling.exposer.DatabaseColumnType;

import javax.lang.model.element.VariableElement;

public class AttributeField {
    private final DatabaseColumnType columnType;
    private Column column;
    private VariableElement variableElement;

    public AttributeField(Column column, VariableElement variableElement) {
        this.column = column;
        this.variableElement = variableElement;
        columnType = TypeUtils.findByType(ClassName.get(variableElement.asType()));
    }

    public Column getColumn() {
        return column;
    }

    public VariableElement getVariableElement() {
        return variableElement;
    }

    public ClassName getAttributeClass() {
        switch (columnType) {
            case INT: return ClassName.get(IntAttribute.class);
            case LONG: return ClassName.get(IntAttribute.class);
            case SHORT: return ClassName.get(IntAttribute.class);
            case BYTE: return ClassName.get(IntAttribute.class);
            case STRING: return ClassName.get(StringAttribute.class);
            case BOOLEAN: return ClassName.get(BooleanAttribute.class);
            //TODO: Implement DoubleAttribute
            case DOUBLE: return ClassName.get(StringAttribute.class);
            case FLOAT: return ClassName.get(StringAttribute.class);
        }
        throw new UnsupportedOperationException();
    }

    public String getFeelingTableMethod() {
        switch (columnType) {
            case INT: return "intCol";
            case LONG: return "intCol";
            case SHORT: return "intCol";
            case BYTE: return "intCol";
            case STRING: return "stringCol";
            case BOOLEAN: return "booleanCol";
            //TODO: Implement DoubleAttribute
            case DOUBLE: return "stringCol";
            case FLOAT: return "stringCol";
        }
        throw new UnsupportedOperationException();
    }

    public String entityGetterMethod() {
        String name = variableElement.getSimpleName().toString();
        name = name.substring(0, 1).toUpperCase() + name.substring(1);
        if (columnType == DatabaseColumnType.BOOLEAN) {
            return "is" + name;
        }
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

}
