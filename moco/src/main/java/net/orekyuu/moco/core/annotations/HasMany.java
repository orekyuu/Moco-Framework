package net.orekyuu.moco.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
public @interface HasMany {
    String foreignKey();

    String key();

    String variableName() default "";
}
