package net.orekyuu.moco.core;

import net.orekyuu.moco.core.entity.Post;
import net.orekyuu.moco.core.entity.Posts;
import net.orekyuu.moco.core.entity.User;
import net.orekyuu.moco.core.entity.Users;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class PreloadTest extends DatabaseTest {

    @Test
    public void preloadHasMany() {
        Users.create(new User(-1, "foo", true));
        User user = Users.firstOrNull();
        Posts.create(new Post(-1, "title", "contents", user));
        Posts.create(new Post(-1, "title", "contents", user));
        Posts.create(new Post(-1, "title", "contents", user));

        List<User> users = Users.all().preload(Users.USER_TO_POSTS()).toList();

        User resultUser = users.get(0);
        Assertions.assertEquals(resultUser.getPosts().size(), 3);
    }
}
