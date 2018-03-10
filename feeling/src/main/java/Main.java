import net.orekyuu.moco.feeling.Select;
import net.orekyuu.moco.feeling.SqlContext;
import net.orekyuu.moco.feeling.Table;
import net.orekyuu.moco.feeling.TableBuilder;
import net.orekyuu.moco.feeling.node.SqlBindParam;
import net.orekyuu.moco.feeling.node.WhereClause;

public class Main {
    public static void main(String[] args) {
        Table usersTable = new TableBuilder("users")._integer("id")._string("name")._boolean("active").build();
        Table posts = new TableBuilder("posts")._integer("id")._integer("user_id")._string("title").build();

        WhereClause isActive = new WhereClause(usersTable.booleanCol("active").eq(new SqlBindParam(true, Boolean.class)));
        Select select = usersTable.select().where(isActive);
        SqlContext context = select.prepareQuery();
        System.out.println(context.sql());
    }
}
