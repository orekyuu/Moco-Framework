package net.orekyuu.moco.core.annotations;

public @interface Column {
    /**
     * DB上のカラム名
     */
    String name();

    /**
     * 生成されるカラムの定数名
     */
    String variableName() default "";

    /**
     * 生成される値であればtrue
     * trueになっている場合、insertの対象になりません
     */
    boolean generatedValue() default false;
}
