import net.orekyuu.moco.feeling.Select;
import net.orekyuu.moco.feeling.SqlContext;
import net.orekyuu.moco.feeling.Table;
import net.orekyuu.moco.feeling.TableBuilder;
import net.orekyuu.moco.feeling.node.*;

public class Main {
    public static void main(String[] args) {
        Table usersTable = new TableBuilder("users")._integer("id")._string("name")._boolean("active").build();
        Table posts = new TableBuilder("posts")._integer("id")._integer("user_id")._string("title").build();

        SqlJoinClause joinClause = new SqlJoinClause(new SqlLiteral(usersTable.getTableName()));
        joinClause.add(new SqlJoin(
                new SqlLiteral(posts.getTableName()),
                usersTable.intCol("id").eq(posts.intCol("user_id"))
        ));
        WhereClause isActive = new WhereClause(usersTable.booleanCol("active").eq(new SqlBindParam(true, Boolean.class)));
        Select select = usersTable.select().from(joinClause).where(isActive);
        SqlContext context = select.prepareQuery();
        System.out.println(context.sql());
    }
}
