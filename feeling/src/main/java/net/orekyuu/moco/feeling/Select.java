package net.orekyuu.moco.feeling;

import net.orekyuu.moco.feeling.attributes.Attribute;
import net.orekyuu.moco.feeling.node.FromClause;
import net.orekyuu.moco.feeling.node.SqlJoin;
import net.orekyuu.moco.feeling.node.SqlJoinClause;
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
    private List<Attribute> resultColumn = new ArrayList<>();

    public Select from(Table table) {
        return from(new SqlJoinClause(table));
    }

    public Select from(SqlJoinClause joinClause) {
        resultColumn.clear();
        addResultColumn(joinClause.getTable());
        joinClause.getJoins().stream()
                .map(SqlJoin::table)
                .forEach(this::addResultColumn);

        fromClause = new FromClause(joinClause);
        return this;
    }

    public Select where(WhereClause whereClause) {
        this.whereClause = whereClause;
        return this;
    }

    public void addResultColumn(Attribute attribute) {
        resultColumn.add(attribute);
    }

    public void addResultColumn(Table table) {
        table.getColumns().forEach(this::addResultColumn);
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

    public List<Attribute> getResultColumn() {
        return resultColumn;
    }
}
