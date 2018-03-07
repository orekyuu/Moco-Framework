package net.orekyuu.moco.feeling;

import java.util.*;
import java.util.stream.Collectors;

public class Select {

    private Set<Column<?>> selectFields = new HashSet<>();
    private FromClause fromClause;
    private WhereClause whereClause;
    private OptionalInt limit = OptionalInt.empty();
    private OptionalInt offset = OptionalInt.empty();

    public Select select(Set<Column<?>> fields) {
        selectFields = fields;
        return this;
    }

    public Select from(Table table) {
        fromClause = new FromClause(table);
        return this;
    }

    public Select where(WhereClause whereClause) {
        this.whereClause = whereClause;
        return this;
    }

    public SqlContext prepareQuery() {
        SqlContext context = new SqlContext();
        context.append("select");
        String fields = selectFields.stream().map(Column::fullColumnName).collect(Collectors.joining(", ", " ", " "));
        context.append(fields);

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
