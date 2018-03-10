package net.orekyuu.moco.feeling;

import net.orekyuu.moco.feeling.node.FromClause;
import net.orekyuu.moco.feeling.node.SqlJoinClause;
import net.orekyuu.moco.feeling.node.SqlLiteral;
import net.orekyuu.moco.feeling.node.WhereClause;
import net.orekyuu.moco.feeling.visitor.MySqlVisitor;
import net.orekyuu.moco.feeling.visitor.SqlVisitor;

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
        SqlVisitor visitor = new MySqlVisitor();
        accept(visitor, context);
        return context;
    }

    public List<Map<String, Object>> executeQuery() {
        return new ArrayList<>();
    }

    private void accept(SqlVisitor visitor, SqlContext context) {
        visitor.visit(this, context);
    }

    public FromClause getFromClause() {
        return fromClause;
    }

    public WhereClause getWhereClause() {
        return whereClause;
    }
}
