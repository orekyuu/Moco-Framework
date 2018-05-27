package net.orekyuu.moco.chou.visitor;

import net.orekyuu.moco.chou.CompilerException;
import net.orekyuu.moco.chou.RoundContext;
import net.orekyuu.moco.chou.attribute.AttributeField;
import net.orekyuu.moco.chou.attribute.AttributeFieldFactory;
import net.orekyuu.moco.chou.attribute.UnsupportedAttributeFieldException;
import net.orekyuu.moco.core.annotations.Column;

import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementScanner8;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ColumnFieldVisitor extends ElementScanner8<Void, Void> {

    private final List<AttributeField> attrs = new ArrayList<>();
    private final RoundContext context;

    public ColumnFieldVisitor(RoundContext context) {
        this.context = context;
    }

    @Override
    public Void visitVariable(VariableElement e, Void aVoid) {
        AttributeFieldFactory fieldFactory = new AttributeFieldFactory();
        Optional.ofNullable(e.getAnnotation(Column.class)).ifPresent(column -> {
            try {
                AttributeField attributeField = fieldFactory.create(context, column, e);
                attrs.add(attributeField);
            } catch (UnsupportedAttributeFieldException e1) {
                throw new CompilerException(e, "サポートされていない型です。");
            }
        });
        return super.visitVariable(e, aVoid);
    }

    public List<AttributeField> getAttrs() {
        return attrs;
    }
}
