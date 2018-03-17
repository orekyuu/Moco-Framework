package net.orekyuu.moco.chou;

import com.squareup.javapoet.ClassName;
import net.orekyuu.moco.core.annotations.Column;

import javax.lang.model.element.VariableElement;

public class ColumnField {
    private final DatabaseColumnType columnType;
    private Column column;
    private VariableElement variableElement;

    public ColumnField(Column column, VariableElement variableElement) {
        this.column = column;
        this.variableElement = variableElement;
        columnType = DatabaseColumnType.findSupportedType(variableElement).orElseThrow(RuntimeException::new);
    }

    public Column getColumn() {
        return column;
    }

    public VariableElement getVariableElement() {
        return variableElement;
    }

    public ClassName getAttributeClass() {
        return ClassName.get(columnType.getAttribute());
    }

    public String getFeelingTableMethod() {
        return columnType.getFeelingTableMethod();
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
