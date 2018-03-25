package net.orekyuu.moco.feeling;

import net.orekyuu.moco.feeling.node.FromClause;
import net.orekyuu.moco.feeling.node.SqlJoinClause;
import net.orekyuu.moco.feeling.node.SqlLimit;
import net.orekyuu.moco.feeling.node.WhereClause;
import net.orekyuu.moco.feeling.visitor.MySqlVisitor;
import net.orekyuu.moco.feeling.visitor.SqlVisitor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Delete {

    private FromClause fromClause;
    private WhereClause whereClause;
    private SqlLimit limit = null;

    public Delete from(Table table) {
        fromClause = new FromClause(new SqlJoinClause(table));
        return this;
    }

    public Delete where(WhereClause whereClause) {
        this.whereClause = whereClause;
        return this;
    }

    public Delete limit(SqlLimit limit) {
        this.limit = limit;
        return this;
    }


    public SqlContext prepareQuery() {
        SqlContext context = new SqlContext();
        SqlVisitor visitor = new MySqlVisitor();
        accept(visitor, context);
        return context;
    }

    public int executeQuery(Connection connection) throws UncheckedSQLException {
        SqlContext context = prepareQuery();
        try (PreparedStatement statement = context.createStatement(connection)) {
            return statement.executeUpdate();
        } catch (SQLException e) {
            throw new UncheckedSQLException(e);
        }
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

    public SqlLimit getLimit() {
        return limit;
    }

}
