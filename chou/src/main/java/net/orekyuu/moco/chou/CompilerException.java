package net.orekyuu.moco.chou;

import javax.lang.model.element.Element;

public class CompilerException extends RuntimeException {
    private final Element element;

    public CompilerException(Element element, String message) {
        super(message);
        this.element = element;
    }

    public Element getElement() {
        return element;
    }
}
