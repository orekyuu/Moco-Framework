package net.orekyuu.moco.feeling;

public class SqlContext {
    private StringBuilder builder = new StringBuilder();

    public SqlContext append(String str) {
        builder.append(str);
        return this;
    }

    public SqlContext append(int integer) {
        builder.append(integer);
        return this;
    }

    public String sql() {
        return builder.toString();
    }
}
