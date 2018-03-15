package net.orekyuu.moco.chou;

import com.squareup.javapoet.JavaFile;
import net.orekyuu.moco.core.annotations.Column;
import net.orekyuu.moco.core.annotations.Table;
import net.orekyuu.moco.feeling.util.Pair;

import javax.annotation.processing.Messager;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementScanner8;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TableClassScanner extends ElementScanner8<Void, Void> {

    private final Table table;
    private final Messager messager;
    private PackageElement packageElement;
    private TypeElement originalType;
    private List<Pair<Column, VariableElement>> columnVariablePairs = new ArrayList<>();

    public TableClassScanner(Table table, Messager messager) {
        this.table = table;
        this.messager = messager;
    }

    @Override
    public Void visitType(TypeElement e, Void aVoid) {
        if (e.getAnnotation(Table.class) != null) {
            originalType = e;
        }
        return super.visitType(e, aVoid);
    }

    @Override
    public Void visitVariable(VariableElement e, Void aVoid) {
        Column column = e.getAnnotation(Column.class);
        if (column != null) {
            columnVariablePairs.add(Pair.of(column, e));
        }

        return super.visitVariable(e, aVoid);
    }

    public List<JavaFile> generatedFiles() {
        return Arrays.asList();
    }
}
