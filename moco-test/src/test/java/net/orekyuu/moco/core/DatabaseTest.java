package net.orekyuu.moco.core;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.sql.SQLException;
import java.util.Map;
import java.util.Optional;

public abstract class DatabaseTest {

    @BeforeEach
    public void before() throws SQLException {
        MysqlDataSource dataSource = new MysqlDataSource();
        Map<String, String> env = System.getenv();
        dataSource.setURL("jdbc:mysql://" + env.getOrDefault("MYSQL_HOST", "localhost") + ":3306/" + env.getOrDefault("MYSQL_DATABASE", "moco_test"));
        Optional<String> mysqlUser = Optional.ofNullable(System.getenv("MYSQL_USER"));
        dataSource.setUser(mysqlUser.orElse("moco"));

        if (!mysqlUser.isPresent()) {
            dataSource.setPassword("moco");
        }
        ConnectionManager.initialize(dataSource, DataSourceType.MYSQL);
        ConnectionManager.getConnection().setAutoCommit(false);
    }

    @AfterEach
    public void after() throws SQLException {
        ConnectionManager.getConnection().rollback();
    }
}
