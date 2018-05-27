package net.orekyuu.moco.feeling;

import net.orekyuu.moco.feeling.node.*;
import net.orekyuu.moco.feeling.visitor.SqlVisitor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Delete {

    private FromClause fromClause;
    private WhereClause whereClause;
    private SqlLimit limit = null;
    private final List<SqlOrderigTerm> orders = new ArrayList<>();

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

    public Delete order(SqlOrderigTerm term) {
        orders.add(term);
        return this;
    }

    public Delete order(SqlOrderigTerm ... term) {
        orders.addAll(Arrays.asList(term));
        return this;
    }

    public Delete order(Iterable<SqlOrderigTerm> terms) {
        terms.forEach(orders::add);
        return this;
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

    public Optional<SqlOrderBy> getOrderBy() {
        if (orders.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new SqlOrderBy(new SqlNodeArray(orders)));
    }
}
