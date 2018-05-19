package net.orekyuu.moco.feeling;

import net.orekyuu.moco.feeling.attributes.Attribute;
import net.orekyuu.moco.feeling.node.SqlNodeArray;
import net.orekyuu.moco.feeling.visitor.MySqlVisitor;
import net.orekyuu.moco.feeling.visitor.SqlVisitor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Insert {
    private Table table;
    private List<Attribute> attributes = new ArrayList<>();
    private SqlNodeArray values;

    public Insert(Table table) {
        this.table = table;
    }

    public void setAttributes(List<Attribute> attributes) {
        this.attributes = attributes;
    }

    public void setValues(SqlNodeArray values) {
        this.values = values;
    }

    public Table getTable() {
        return table;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public SqlNodeArray getValues() {
        return values;
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
}
