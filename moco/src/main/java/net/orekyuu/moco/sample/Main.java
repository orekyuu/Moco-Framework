package net.orekyuu.moco.sample;

import com.mysql.cj.jdbc.MysqlDataSource;
import net.orekyuu.moco.core.ConnectionManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {
        setup();

        // トランザクションは後で作る
        Connection connection = ConnectionManager.getConnection();
        connection.setAutoCommit(false);

        // create
        Users.create(new User(-1, "orekyuu", true));
        Users.create(new User(-1, "orekyuu", false));
        Users.create(new User(-1, "hogekyuu", true));
        Users.create(new User(-1, "fugakyuu", false));

        // find first
        User first = Users.first();
        System.out.println(first);

        // find all
        UserList userList = Users.all();
        List<User> users = userList.toList();
        System.out.println(users);

        // where active
        UserList activeUserList = Users.all().where(Users.ACTIVE.isTrue());
        System.out.println(activeUserList.toList());

        // find activated orekyuu
        UserList orekyuu = Users.all().where(Users.NAME.eq("orekyuu"), Users.ACTIVE.isTrue());
        System.out.println(orekyuu.toList());

        connection.rollback();
    }

    public static void setup() {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://localhost:3306/moco_development");
        dataSource.setUser("root");
        ConnectionManager.initialize(dataSource);
    }
}
