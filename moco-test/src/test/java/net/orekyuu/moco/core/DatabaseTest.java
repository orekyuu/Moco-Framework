package net.orekyuu.moco.core;

import com.mysql.cj.jdbc.MysqlDataSource;
import net.orekyuu.moco.core.ConnectionManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.SQLException;

public abstract class DatabaseTest {

    @BeforeEach
    public void before() throws SQLException {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://localhost:3306/moco_test");
        dataSource.setUser("moco");
        dataSource.setPassword("moco");
        ConnectionManager.initialize(dataSource);
        ConnectionManager.getConnection().setAutoCommit(false);
    }

    @AfterEach
    public void after() throws SQLException {
        ConnectionManager.getConnection().rollback();
    }
}
