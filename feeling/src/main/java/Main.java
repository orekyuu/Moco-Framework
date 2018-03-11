import net.orekyuu.moco.feeling.*;
import net.orekyuu.moco.feeling.node.SqlBindParam;
import net.orekyuu.moco.feeling.node.SqlNodeArray;
import net.orekyuu.moco.feeling.node.WhereClause;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Table usersTable = new TableBuilder("users")._integer("id")._string("name")._boolean("active").build();
        Table posts = new TableBuilder("posts")._integer("id")._integer("user_id")._string("title").build();

        WhereClause isActive = new WhereClause(usersTable.booleanCol("active").eq(new SqlBindParam(true, Boolean.class)));
        Select select = usersTable.select().where(isActive);
        SqlContext context = select.prepareQuery();
        System.out.println(context.toString());

        Insert insert = new Insert(usersTable);
        insert.setAttributes(Arrays.asList(
                usersTable.stringCol("name"),
                usersTable.booleanCol("active")
        ));
        insert.setValues(new SqlNodeArray(Arrays.asList(new SqlBindParam("orekyuu", String.class), new SqlBindParam(true, Boolean.class))));

        SqlContext insertContext = insert.prepareQuery();
        System.out.println(insertContext.toString());
    }
}
