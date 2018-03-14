package net.orekyuu.moco.feeling;

import net.orekyuu.moco.feeling.node.SqlBindParam;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlContext {
    interface SqlParamSetter {
        void bind(int index, SqlBindParam param, PreparedStatement statement) throws SQLException;
    }

    private StringBuilder builder = new StringBuilder();
    private List<SqlBindParam> bindParams = new ArrayList<>();
    private static final Map<Class, SqlParamSetter> paramSetters = new HashMap<>();

    static {
        paramSetters.put(String.class, (index, param, statement) -> statement.setString(index, (String)param.getValue()));
        paramSetters.put(Boolean.class, (index, param, statement) -> statement.setBoolean(index, (Boolean)param.getValue()));
        paramSetters.put(Integer.class, (index, param, statement) -> statement.setInt(index, (Integer)param.getValue()));
        paramSetters.put(Long.class, (index, param, statement) -> statement.setLong(index, (Long)param.getValue()));
        paramSetters.put(Double.class, (index, param, statement) -> statement.setDouble(index, (Double) param.getValue()));
        paramSetters.put(Float.class, (index, param, statement) -> statement.setDouble(index, (Float) param.getValue()));
        paramSetters.put(BigDecimal.class, (index, param, statement) -> statement.setBigDecimal(index, (BigDecimal) param.getValue()));
        paramSetters.put(Timestamp.class, (index, param, statement) -> statement.setTimestamp(index, (Timestamp) param.getValue()));
        paramSetters.put(byte[].class, (index, param, statement) -> statement.setBytes(index, (byte[]) param.getValue()));
    }

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

    public PreparedStatement createStatement(Connection connection) throws SQLException {
        System.out.println(sql());
        PreparedStatement statement = connection.prepareStatement(sql());
        int index = 1;
        for (SqlBindParam bindParam : bindParams) {
            Class clazz = bindParam.getClazz();
            SqlParamSetter paramSetter = paramSetters.get(clazz);
            if (paramSetter == null) {
                throw new IllegalArgumentException(bindParam.toString());
            }
            paramSetter.bind(index, bindParam, statement);
            index++;
        }
        return statement;
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
