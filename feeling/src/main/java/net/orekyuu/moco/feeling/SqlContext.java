package net.orekyuu.moco.feeling;

import net.orekyuu.moco.feeling.node.SqlBindParam;

import java.util.ArrayList;
import java.util.List;

public class SqlContext {
    private StringBuilder builder = new StringBuilder();
    private List<SqlBindParam> bindParams = new ArrayList<>();

    public SqlContext append(String str) {
        builder.append(str);
        return this;
    }

    public SqlContext append(int integer) {
        builder.append(integer);
        return this;
    }

    public SqlContext appendParams(SqlBindParam param) {
        append("?");
        bindParams.add(param);
        return this;
    }

    public String sql() {
        return builder.toString().trim();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("SqlContext{");
        sb.append("builder=").append(builder);
        sb.append(", bindParams=").append(bindParams);
        sb.append('}');
        return sb.toString();
    }
}
