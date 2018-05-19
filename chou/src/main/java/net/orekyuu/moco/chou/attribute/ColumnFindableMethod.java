package net.orekyuu.moco.chou.attribute;

public enum ColumnFindableMethod {
    INT("intCol"),
    LONG("longCol"),
    STRING("stringCol"),
    BOOLEAN("booleanCol"),
    BIG_DECIMAL("decimalCol"),
    FLOAT("floatCol"),
    TIME("timeCol");

    private final String methodName;

    ColumnFindableMethod(String methodName) {
        this.methodName = methodName;
    }

    public String getMethodName() {
        return methodName;
    }
}
