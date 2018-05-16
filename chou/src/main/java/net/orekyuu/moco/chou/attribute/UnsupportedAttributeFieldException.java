package net.orekyuu.moco.chou.attribute;

import javax.lang.model.element.VariableElement;

/**
 * サポートされていないAttributeFieldを見つけた時の例外
 */
public class UnsupportedAttributeFieldException extends Exception {
    private final VariableElement variableElement;

    public UnsupportedAttributeFieldException(VariableElement variableElement) {
        this.variableElement = variableElement;
    }

    public VariableElement getVariableElement() {
        return variableElement;
    }
}
