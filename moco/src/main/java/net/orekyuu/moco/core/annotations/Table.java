package net.orekyuu.moco.core.annotations;

public @interface Table {
    String name();

    boolean immutable() default false;
}
