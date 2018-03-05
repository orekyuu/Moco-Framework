import net.orekyuu.moco.feeling.SqlContext;
import net.orekyuu.moco.feeling.Table;
import net.orekyuu.moco.feeling.TableBuilder;

public class Main {
    public static void main(String[] args) {
        Table users = new TableBuilder("users").integer("id").build();
        SqlContext context = users.select().where(users.intCol("id").eq(1)).prepareQuery();
        System.out.println(context.sql());
    }
}
