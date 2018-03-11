package net.orekyuu.moco.core;

import net.orekyuu.moco.feeling.UncheckedSQLException;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {
    private static ThreadLocal<Connection> threadLocalConnection = new ThreadLocal<>();
    private static DataSource dataSource;

    public static void initialize(DataSource dataSource) {
        ConnectionManager.dataSource = dataSource;
    }

    public static synchronized Connection getConnection() {
        try {
            Connection connection = ConnectionManager.threadLocalConnection.get();
            if (connection == null) {
                connection = dataSource.getConnection();
                threadLocalConnection.set(connection);
            }
            return connection;
        } catch (SQLException e) {
            throw new UncheckedSQLException(e);
        }
    }
}
