package net.orekyuu.moco.sample;


import net.orekyuu.moco.core.ConnectionManager;
import net.orekyuu.moco.core.RecordNotFoundException;
import net.orekyuu.moco.core.attribute.BooleanAttribute;
import net.orekyuu.moco.core.attribute.IntAttribute;
import net.orekyuu.moco.core.attribute.StringAttribute;
import net.orekyuu.moco.core.relation.HasManyRelation;
import net.orekyuu.moco.feeling.Insert;
import net.orekyuu.moco.feeling.Select;
import net.orekyuu.moco.feeling.Table;
import net.orekyuu.moco.feeling.TableBuilder;
import net.orekyuu.moco.feeling.node.SqlBindParam;
import net.orekyuu.moco.feeling.node.SqlNodeArray;

import java.util.Arrays;
import java.util.List;

public class Users {


    // mapper
    public static final Select.QueryResultMapper<User> MAPPER = resultSet -> new User(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getBoolean("active"));
    // table
    public static final Table TABLE = new TableBuilder("users", MAPPER)._integer("id")._string("name")._boolean("active").build();

    // columns
    public static final IntAttribute<User> ID = new IntAttribute<>(TABLE.intCol("id"), User::getId);
    public static final StringAttribute<User> NAME = new StringAttribute<>(TABLE.stringCol("name"), User::getName);
    public static final BooleanAttribute<User> ACTIVE = new BooleanAttribute<>(TABLE.booleanCol("active"), User::isActive);

    // relation
    public static final HasManyRelation<User, Post> POSTS = new HasManyRelation<>(TABLE, ID, Posts.TABLE, Posts.USER_ID, Posts.MAPPER);

    // create
    public static void create(User user) {
        Insert insert = new Insert(TABLE);
        insert.setAttributes(Arrays.asList(NAME.ast(), ACTIVE.ast()));
        insert.setValues(new SqlNodeArray(Arrays.asList(
                new SqlBindParam(user.getName(), String.class),
                new SqlBindParam(user.isActive(), Boolean.class)
        )));
        insert.executeQuery(ConnectionManager.getConnection());
    }

    // finder
    public static User findById(int id) {
        List<User> users = all().where(ID.eq(id)).toList();
        if (users.isEmpty()) {
            throw new RecordNotFoundException();
        }
        return users.get(0);
    }

    public static User first() {
        List<User> users = all().toList();
        return users.isEmpty() ? null : users.get(0);
    }

    public static UserList all() {
        return new UserList(TABLE.select());
    }

}
