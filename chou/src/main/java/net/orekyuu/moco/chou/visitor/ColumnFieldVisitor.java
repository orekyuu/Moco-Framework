package net.orekyuu.moco.chou.visitor;

import net.orekyuu.moco.chou.AttributeField;
import net.orekyuu.moco.core.annotations.Column;

import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementScanner8;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ColumnFieldVisitor extends ElementScanner8<Void, Void> {

    private List<AttributeField> attrs = new ArrayList<>();

    @Override
    public Void visitVariable(VariableElement e, Void aVoid) {
        Optional.ofNullable(e.getAnnotation(Column.class)).ifPresent(column -> attrs.add(new AttributeField(column, e)));
        return super.visitVariable(e, aVoid);
    }

    public List<AttributeField> getAttrs() {
        return attrs;
    }
}
