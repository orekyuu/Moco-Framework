package net.orekyuu.moco.feeling;

import net.orekyuu.moco.feeling.attributes.Attribute;
import net.orekyuu.moco.feeling.node.*;
import net.orekyuu.moco.feeling.visitor.SqlVisitor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Update {
    private Table table;
    private List<SqlColumnNameExprPair> pairs = new ArrayList<>();
    private WhereClause whereClause;

    public Update(Table table) {
        this.table = table;
    }

    public void addSetValue(Attribute attribute, SqlNode sqlNode) {
        pairs.add(new SqlColumnNameExprPair(attribute, sqlNode));
    }

    public void where(WhereClause whereClause) {
        this.whereClause = whereClause;
    }

    public Table getTable() {
        return table;
    }

    public List<SqlColumnNameExprPair> getPairs() {
        return pairs;
    }

    public WhereClause getWhereClause() {
        return whereClause;
    }

    private void accept(SqlVisitor visitor, SqlContext context) {
        visitor.visit(this, context);
    }

    public SqlContext prepareQuery(SqlVisitor sqlVisitor) {
        SqlContext context = new SqlContext();
        accept(sqlVisitor, context);
        return context;
    }

    public int executeQuery(Connection connection, SqlVisitor sqlVisitor) throws UncheckedSQLException {
        SqlContext context = prepareQuery(sqlVisitor);
        try (PreparedStatement statement = context.createStatement(connection)) {
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new UncheckedSQLException(e);
        }
    }
}
