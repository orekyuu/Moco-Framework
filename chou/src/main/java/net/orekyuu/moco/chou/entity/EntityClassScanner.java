package net.orekyuu.moco.chou.entity;

import com.squareup.javapoet.JavaFile;
import net.orekyuu.moco.chou.AttributeField;
import net.orekyuu.moco.core.annotations.*;

import javax.annotation.processing.Messager;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementScanner8;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class EntityClassScanner extends ElementScanner8<Void, Void> {

    private final Messager messager;
    private final Elements elementUtils;
    private final Types typeUtils;
    private EntityClass.Builder originalEntityBuilder = new EntityClass.Builder();

    public EntityClassScanner(Table table, Elements elementUtils, Types typeUtils, Messager messager) {
        originalEntityBuilder.table(table);
        this.elementUtils = elementUtils;
        this.messager = messager;
        this.typeUtils = typeUtils;
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
        Optional.ofNullable(e.getAnnotation(Column.class))
                .ifPresent(column -> originalEntityBuilder.addColumnField(new AttributeField(column, e)));

        Optional.ofNullable(e.getAnnotation(HasMany.class))
                .ifPresent(hasMany -> originalEntityBuilder.addRelationField(new HasManyRelationField(e, hasMany)));

        Optional.ofNullable(e.getAnnotation(HasOne.class))
                .ifPresent(hasOne -> originalEntityBuilder.addRelationField(new HasOneRelationField(e, hasOne)));

        Optional.ofNullable(e.getAnnotation(BelongsTo.class))
                .ifPresent(belongsTo -> originalEntityBuilder.addRelationField(new BelongsToRelationField(e, belongsTo)));

        return super.visitVariable(e, aVoid);
    }

    public List<JavaFile> generatedFiles() {
        EntityClass entityClass = originalEntityBuilder.build();
        TableClass tableClass = new TableClass(entityClass);
        EntityListClass entityListClass = new EntityListClass(entityClass);

        return Arrays.asList(
                tableClass.createJavaFile(messager),
                entityListClass.createJavaFile(messager, tableClass)
        );
    }
}
