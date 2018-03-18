package net.orekyuu.moco.chou.entity;

import com.squareup.javapoet.JavaFile;
import net.orekyuu.moco.chou.AttributeField;
import net.orekyuu.moco.core.annotations.Column;
import net.orekyuu.moco.core.annotations.Table;

import javax.annotation.processing.Messager;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementScanner8;
import javax.lang.model.util.Elements;
import java.util.Arrays;
import java.util.List;

public class EntityClassScanner extends ElementScanner8<Void, Void> {

    private final Messager messager;
    private final Elements elementUtils;
    private EntityClass.Builder originalEntityBuilder = new EntityClass.Builder();

    public EntityClassScanner(Table table, Elements elementUtils, Messager messager) {
        originalEntityBuilder.table(table);
        this.elementUtils = elementUtils;
        this.messager = messager;
    }

    @Override
    public Void visitType(TypeElement e, Void aVoid) {
        if (e.getAnnotation(Table.class) != null) {
            originalEntityBuilder.originalType(e);
            originalEntityBuilder.packageElement(elementUtils.getPackageOf(e));
        }
        return super.visitType(e, aVoid);
    }

    @Override
    public Void visitVariable(VariableElement e, Void aVoid) {
        Column column = e.getAnnotation(Column.class);
        if (column != null) {
            originalEntityBuilder.addColumnField(new AttributeField(column, e));
        }

        return super.visitVariable(e, aVoid);
    }

    public List<JavaFile> generatedFiles() {
        EntityClass entityClass = originalEntityBuilder.build();
        TableClass tableClass = new TableClass(entityClass);
        return Arrays.asList(
                tableClass.createJavaFile(messager),
                new EntityListClassFactory(entityClass).createJavaFile(messager)
        );
    }
}
