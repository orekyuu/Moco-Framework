package net.orekyuu.moco.chou.entity;

import com.squareup.javapoet.JavaFile;
import net.orekyuu.moco.chou.CompilerException;
import net.orekyuu.moco.chou.RoundContext;
import net.orekyuu.moco.chou.attribute.AttributeFieldFactory;
import net.orekyuu.moco.chou.attribute.UnsupportedAttributeFieldException;
import net.orekyuu.moco.core.annotations.*;

import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.ElementScanner8;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class EntityClassScanner extends ElementScanner8<Void, Void> {

    private final RoundContext roundContext;
    private EntityClass.Builder originalEntityBuilder = new EntityClass.Builder();

    public EntityClassScanner(Table table, RoundContext roundContext) {
        originalEntityBuilder.table(table);
        this.roundContext = roundContext;
    }

    @Override
    public Void visitType(TypeElement e, Void aVoid) {
        if (e.getAnnotation(Table.class) != null) {
            originalEntityBuilder.originalType(e);
            originalEntityBuilder.packageElement(roundContext.getProcessingEnv().getElementUtils().getPackageOf(e));
        }
        return super.visitType(e, aVoid);
    }

    @Override
    public Void visitVariable(VariableElement e, Void aVoid) {
        AttributeFieldFactory attributeFieldFactory = new AttributeFieldFactory();
        Optional.ofNullable(e.getAnnotation(Column.class))
                .ifPresent(column -> {
                    try {
                        originalEntityBuilder.addColumnField(attributeFieldFactory.create(roundContext, column, e));
                    } catch (UnsupportedAttributeFieldException e1) {
                        throw new CompilerException(e, "サポートされていないColumnの型です。");
                    }
                });

        Optional.ofNullable(e.getAnnotation(HasMany.class))
                .ifPresent(hasMany -> originalEntityBuilder.addRelationField(new HasManyRelationField(roundContext, e, hasMany)));

        Optional.ofNullable(e.getAnnotation(HasOne.class))
                .ifPresent(hasOne -> originalEntityBuilder.addRelationField(new HasOneRelationField(roundContext, e, hasOne)));

        Optional.ofNullable(e.getAnnotation(BelongsTo.class))
                .ifPresent(belongsTo -> originalEntityBuilder.addRelationField(new BelongsToRelationField(roundContext, e, belongsTo)));

        return super.visitVariable(e, aVoid);
    }

    public List<JavaFile> generatedFiles() {
        EntityClass entityClass = originalEntityBuilder.build();
        TableClass tableClass = new TableClass(entityClass);
        EntityListClass entityListClass = new EntityListClass(entityClass);

        return Arrays.asList(
                tableClass.createJavaFile(roundContext),
                entityListClass.createJavaFile(roundContext, tableClass)
        );
    }
}
