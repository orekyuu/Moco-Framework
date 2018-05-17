package net.orekyuu.moco.chou.attribute;

import net.orekyuu.moco.chou.AnnotationProcessHelper;
import net.orekyuu.moco.chou.RoundContext;
import net.orekyuu.moco.core.annotations.Column;
import net.orekyuu.moco.feeling.util.Pair;

import javax.annotation.Nonnull;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class AttributeFieldFactory {

    private interface Predicate {
        boolean isSupport(RoundContext context, Column column, VariableElement fieldType);
    }

    private interface Factory {
        AttributeField create(RoundContext context, Column column, VariableElement fieldType);
    }

    private List<Pair<Factory, Predicate>> predicates = new ArrayList<>();

    public AttributeFieldFactory() {
        register(IntAttributeField::new, (context, column, fieldType) -> isSupportedClass(fieldType, int.class, Integer.class));
        register(StringAttributeField::new, (context, column, fieldType) -> isSupportedClass(fieldType, String.class));
        register(BooleanAttributeField::new, (context, column, fieldType) -> isSupportedClass(fieldType, boolean.class, Boolean.class));
        register(LocalDateTimeAttributeField::new, (context, column, fieldType) -> isSupportedClass(fieldType, LocalDateTime.class));

        // Enum
        register(EnumAttributeField::new, (context, column, fieldType) -> isEnum(context, fieldType));
    }

    private void register(Factory factory, Predicate predicate) {
        predicates.add(Pair.of(factory, predicate));
    }

    private boolean isSupportedClass(VariableElement fieldType, Class<?> ... classes) {
        TypeMirror type = fieldType.asType();
        return Arrays.stream(classes).map(Class::getName).anyMatch(className -> Objects.equals(className, type.toString()));
    }

    private boolean isEnum(RoundContext context, VariableElement fieldType) {
        ProcessingEnvironment processingEnv = context.getProcessingEnv();
        TypeMirror field = fieldType.asType();
        if (field.getKind() != TypeKind.DECLARED) {
            return false;
        }
        TypeMirror enumType = AnnotationProcessHelper.getTypeMirrorFromClass(context, Enum.class, field);
        return processingEnv.getTypeUtils().isAssignable(field, enumType);
    }

    @Nonnull
    public AttributeField create(RoundContext context, Column column, VariableElement fieldType) throws UnsupportedAttributeFieldException {
        Factory factory = predicates.stream()
                .filter(pair -> pair.getSecond().isSupport(context, column, fieldType))
                .map(Pair::getFirst)
                .findFirst()
                .orElseThrow(() -> new UnsupportedAttributeFieldException(fieldType));
        return factory.create(context, column, fieldType);
    }
}
