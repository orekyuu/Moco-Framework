package net.orekyuu.moco.feeling;

import net.orekyuu.moco.feeling.attributes.Attribute;
import net.orekyuu.moco.feeling.node.*;
import net.orekyuu.moco.feeling.visitor.SqlVisitor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Select {

    private FromClause fromClause;
    private WhereClause whereClause;
    private SqlLimit limit = null;
    private SqlOffset offset = null;
    private final List<Attribute> resultColumn = new ArrayList<>();
    private final List<SqlOrderigTerm> orders = new ArrayList<>();

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

    public Select order(SqlOrderigTerm term) {
        orders.add(term);
        return this;
    }

    public Select order(SqlOrderigTerm ... term) {
        orders.addAll(Arrays.asList(term));
        return this;
    }

    public Select order(Iterable<SqlOrderigTerm> terms) {
        terms.forEach(orders::add);
        return this;
    }

    public Select offset(SqlOffset offset) {
        this.offset = offset;
        return this;
    }

    public Select limit(SqlLimit limit) {
        this.limit = limit;
        return this;
    }

    public void addResultColumn(Attribute attribute) {
        resultColumn.add(attribute);
    }

    public void addResultColumn(Table table) {
        table.getColumns().forEach(this::addResultColumn);
    }

    public SqlContext prepareQuery(SqlVisitor sqlVisitor) {
        SqlContext context = new SqlContext();
        accept(sqlVisitor, context);
        return context;
    }

    public interface QueryResultListener {
        void onResult(ResultSet resultSet) throws SQLException, ReflectiveOperationException;
    }

    public void executeQueryWithResultListener(Connection connection, SqlVisitor sqlVisitor, QueryResultListener listener) {
        SqlContext context = prepareQuery(sqlVisitor);
        try (PreparedStatement statement = context.createStatement(connection)) {
            ResultSet resultSet = statement.executeQuery();
            listener.onResult(resultSet);
        } catch (SQLException e) {
            throw new UncheckedSQLException(e);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public interface QueryResultMapper<R> {
        R mapping(ResultSet resultSet) throws SQLException, ReflectiveOperationException;
    }
    public <R> List<R> executeQuery(Connection connection, SqlVisitor sqlVisitor, QueryResultMapper<R> mapper) {
        ArrayList<R> list = new ArrayList<>();
        executeQueryWithResultListener(connection, sqlVisitor, resultSet -> {
            while (resultSet.next()) {
                R record = mapper.mapping(resultSet);
                list.add(record);
            }
        });
        return list;
    }

    private void accept(SqlVisitor visitor, SqlContext context) {
        visitor.visit(this, context);
    }

    public FromClause getFromClause() {
        return fromClause;
    }

    public Optional<WhereClause> getWhereClause() {
        return Optional.ofNullable(whereClause);
    }

    public List<Attribute> getResultColumn() {
        return resultColumn;
    }

    public Optional<SqlLimit> getLimit() {
        return Optional.ofNullable(limit);
    }

    public Optional<SqlOffset> getOffset() {
        return Optional.ofNullable(offset);
    }

    public Optional<SqlOrderBy> getOrderBy() {
        if (orders.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new SqlOrderBy(new SqlNodeArray(orders)));
    }
}
