package net.orekyuu.moco.sample;


import net.orekyuu.moco.core.ConnectionManager;
import net.orekyuu.moco.core.RecordNotFoundException;
import net.orekyuu.moco.core.attribute.IntAttribute;
import net.orekyuu.moco.core.attribute.StringAttribute;
import net.orekyuu.moco.feeling.Insert;
import net.orekyuu.moco.feeling.Select;
import net.orekyuu.moco.feeling.Table;
import net.orekyuu.moco.feeling.TableBuilder;
import net.orekyuu.moco.feeling.node.SqlBindParam;
import net.orekyuu.moco.feeling.node.SqlNodeArray;

import java.util.Arrays;
import java.util.List;

public class Posts {
    // mapper
    public static final Select.QueryResultMapper<Post> MAPPER = resultSet -> new Post(resultSet.getInt("id"), resultSet.getInt("user_id"), resultSet.getString("title"));

    // table
    public static final Table TABLE = new TableBuilder("posts", MAPPER)._integer("id")._integer("user_id")._string("title").build();

    // columns
    public static final IntAttribute<Post> ID = new IntAttribute<>(TABLE.intCol("id"), Post::getId);
    public static final IntAttribute<Post> USER_ID = new IntAttribute<>(TABLE.intCol("user_id"), Post::getUserId);
    public static final StringAttribute<Post> TITLE = new StringAttribute<>(TABLE.stringCol("title"), Post::getTitle);

    // create
    public static void create(Post post) {
        Insert insert = new Insert(TABLE);
        insert.setAttributes(Arrays.asList(USER_ID.ast(), TITLE.ast()));
        insert.setValues(new SqlNodeArray(Arrays.asList(
                new SqlBindParam(post.getUserId(), Integer.class),
                new SqlBindParam(post.getTitle(), String.class)
        )));
        insert.executeQuery(ConnectionManager.getConnection());
    }

    // finder
    public static Post findById(int id) {
        List<Post> users = all().where(ID.eq(id)).toList();
        if (users.isEmpty()) {
            throw new RecordNotFoundException();
        }
        return users.get(0);
    }

    public static Post first() {
        List<Post> posts = all().toList();
        return posts.isEmpty() ? null : posts.get(0);
    }

    public static PostList all() {
        return new PostList(TABLE.select());
    }

}
