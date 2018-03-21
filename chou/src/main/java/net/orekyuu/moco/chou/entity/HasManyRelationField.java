package net.orekyuu.moco.chou.entity;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import net.orekyuu.moco.chou.CompilerException;
import net.orekyuu.moco.chou.NamingUtils;
import net.orekyuu.moco.core.ReflectUtil;
import net.orekyuu.moco.core.annotations.HasMany;
import net.orekyuu.moco.core.relation.HasManyRelation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class HasManyRelationField {
    private final VariableElement field;
    private final HasMany hasMany;

    public HasManyRelationField(VariableElement field, HasMany hasMany) {
        this.field = field;
        this.hasMany = hasMany;
    }

    public FieldSpec relationField(TableClass tableClass, EntityClass entityClass) {
        TypeElement childClassElement = getChildClassElement();
        if (childClassElement == null) {
            throw new CompilerException(field, field.getSimpleName() + "はサポートされていない型で宣言されています。");
        }
        ClassName childClassName = ClassName.get(childClassElement);

        FieldSpec.Builder builder = FieldSpec.builder(ParameterizedTypeName.get(ClassName.get(HasManyRelation.class), entityClass.getClassName(), childClassName),
                NamingUtils.toUpperName(entityClass.getClassName().simpleName()) + "_TO_" + NamingUtils.toUpperName(NamingUtils.multipleName(childClassName.simpleName())))
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL);

        ClassName childTableClassName = childTableClassName(childClassName);
        builder.addAnnotation(Nonnull.class)
                .initializer("new $T($N, ID, $T.TABLE, $T.USER_ID, $T.MAPPER, $T.getFieldSetter($T.class, $S))",
                        ParameterizedTypeName.get(ClassName.get(HasManyRelation.class), entityClass.getClassName(), childClassName), tableClass.tableField(), childTableClassName, childTableClassName, childTableClassName, ReflectUtil.class, entityClass.getClassName(), field.getSimpleName());

        return builder.build();
    }

    private ClassName childTableClassName(ClassName childEntityClass) {
        String packageName = childEntityClass.packageName();
        String simpleName = NamingUtils.multipleName(childEntityClass.simpleName());
        return ClassName.get(packageName, simpleName);
    }

    @Nullable
    public TypeElement getChildClassElement() {
        if (!(field.asType() instanceof DeclaredType)) {
            return null;
        }
        DeclaredType fieldType = ((DeclaredType) field.asType());
        ClassName rawFieldType = ((ParameterizedTypeName) ClassName.get(fieldType)).rawType;
        if (Stream.of(List.class, Set.class).noneMatch(it -> ClassName.get(it).equals(rawFieldType))) {
            return null;
        }

        List<? extends TypeMirror> typeArguments = fieldType.getTypeArguments();
        if (typeArguments.size() != 1) {
            return null;
        }
        TypeMirror typeMirror = typeArguments.get(0);
        if (!(typeMirror instanceof DeclaredType)) {
            return null;
        }
        DeclaredType childType = (DeclaredType) typeMirror;
        return (TypeElement) childType.asElement();
    }
}
