package net.orekyuu.moco.core;

import net.orekyuu.moco.feeling.UncheckedSQLException;
import net.orekyuu.moco.feeling.visitor.MySqlVisitor;
import net.orekyuu.moco.feeling.visitor.SqlVisitor;
import net.orekyuu.moco.feeling.visitor.SqliteVisitor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {
    private static final ThreadLocal<Connection> threadLocalConnection = new ThreadLocal<>();
    private static DataSource dataSource;
    private static DataSourceType dataSourceType;

    public static void initialize(DataSource dataSource, DataSourceType dataSourceType) {
        ConnectionManager.dataSource = dataSource;
        ConnectionManager.dataSourceType = dataSourceType;
    }

    public static synchronized Connection getConnection() {
        try {
            Connection connection = ConnectionManager.threadLocalConnection.get();
            if (connection == null || connection.isClosed()) {
                connection = dataSource.getConnection();
                threadLocalConnection.set(connection);
            }
            return connection;
        } catch (SQLException e) {
            throw new UncheckedSQLException(e);
        }
    }

    public static SqlVisitor createSqlVisitor() {
        switch (dataSourceType) {
            case MYSQL: return new MySqlVisitor();
            case SQLITE: return new SqliteVisitor();
            default: throw new IllegalArgumentException("Invalid datasource type: " + dataSourceType);
        }
    }
}
