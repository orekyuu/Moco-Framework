import com.mysql.cj.jdbc.MysqlDataSource;
import net.orekyuu.moco.feeling.*;
import net.orekyuu.moco.feeling.node.SqlBindParam;
import net.orekyuu.moco.feeling.node.SqlNodeArray;
import net.orekyuu.moco.feeling.node.WhereClause;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Table usersTable = new TableBuilder("users")._integer("id")._string("name")._boolean("active").build();
        Table posts = new TableBuilder("posts")._integer("id")._integer("user_id")._string("title").build();

        WhereClause isActive = new WhereClause(usersTable.booleanCol("active").eq(new SqlBindParam(false, Boolean.class)));
        Select select = usersTable.select();
        SqlContext context = select.prepareQuery();
        System.out.println(context.toString());

        Insert insert = new Insert(usersTable);
        insert.setAttributes(Arrays.asList(
                usersTable.stringCol("name"),
                usersTable.booleanCol("active")
        ));
        insert.setValues(new SqlNodeArray(Arrays.asList(new SqlBindParam("hoge", String.class), new SqlBindParam(false, Boolean.class))));

        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL("jdbc:mysql://localhost:3306/moco_development");
        dataSource.setUser("root");
        try(Connection connection = dataSource.getConnection()) {
            select.executeQuery(connection, (Select.QueryResultMapper<Map<String, Object>>) resultSet -> {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                boolean active = resultSet.getBoolean("active");
                System.out.println(String.join(", ", String.valueOf(id), name, String.valueOf(active)));
                return new HashMap<>();
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
