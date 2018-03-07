package net.orekyuu.moco.feeling;

import net.orekyuu.moco.feeling.node.FromClause;
import net.orekyuu.moco.feeling.node.SqlJoinClause;
import net.orekyuu.moco.feeling.node.SqlLiteral;
import net.orekyuu.moco.feeling.node.WhereClause;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;

public class Select {

    private FromClause fromClause;
    private WhereClause whereClause;
    private OptionalInt limit = OptionalInt.empty();
    private OptionalInt offset = OptionalInt.empty();

    public Select from(Table table) {
        return from(new SqlJoinClause(new SqlLiteral(table.getTableName())));
    }

    public Select from(SqlJoinClause joinClause) {
        fromClause = new FromClause(joinClause);
        return this;
    }

    public Select where(WhereClause whereClause) {
        this.whereClause = whereClause;
        return this;
    }

    public SqlContext prepareQuery() {
        SqlContext context = new SqlContext();
        context.append("select");

        context.append("from ");
        fromClause.generateSql(context);

        if (whereClause != null) {
            context.append("where ");
            whereClause.generateSql(context);
        }

        offset.ifPresent(i -> context.append("offset ").append(i).append(" "));
        limit.ifPresent(i -> context.append("limit ").append(i).append(" "));
        return context;
    }

    public List<Map<String, Object>> executeQuery() {
        return new ArrayList<>();
    }
}
