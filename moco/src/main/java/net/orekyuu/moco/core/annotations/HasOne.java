package net.orekyuu.moco.core.annotations;

public @interface HasOne {
    String foreignKey();

    String key();
}
