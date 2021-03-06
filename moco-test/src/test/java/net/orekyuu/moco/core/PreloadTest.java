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
        Users.create(new User(-1, "foo", true, User.Gender.MALE));
        User foo = Users.all().where(Users.NAME.eq("foo")).toList().get(0);
        Posts.create(new Post("foo_title1", "contents", foo));
        Posts.create(new Post("foo_title2", "contents", foo));
        Posts.create(new Post("foo_title3", "contents", foo));

        Users.create(new User(-1, "bar", true, User.Gender.MALE));
        User bar = Users.all().where(Users.NAME.eq("bar")).toList().get(0);
        Posts.create(new Post("bar_title1", "contents", bar));
        Posts.create(new Post("bar_title2", "contents", bar));

        List<User> users = Users.all().preload(Users.USER_TO_POSTS).toList();

        User resultUser = users.get(0);
        Assertions.assertEquals(resultUser.getPosts().size(), 3);
        Assertions.assertTrue(resultUser.getPosts().stream().allMatch(post -> post.getTitle().contains("foo")));

        User result2 = users.get(1);
        Assertions.assertEquals(result2.getPosts().size(), 2);
        Assertions.assertTrue(result2.getPosts().stream().allMatch(post -> post.getTitle().contains("bar")));
    }

    @Test
    public void preloadNotUsed() {
        Users.create(new User(-1, "foo", true, User.Gender.MALE));
        Posts.create(new Post("foo_title1", "contents", Users.firstOrNull()));

        List<User> users = Users.all().toList();
        Assertions.assertTrue(users.get(0).getPosts().isEmpty());
    }

    @Test
    public void hasManyRelationNotFound() {
        Users.create(new User(-1, "foo", true, User.Gender.MALE));
        List<User> users = Users.all().preload(Users.USER_TO_POSTS).toList();
        Assertions.assertTrue(users.get(0).getPosts().isEmpty());
    }

    @Test
    void parentRecordNotFound() {
        List<User> users = Users.all().preload(Users.USER_TO_POSTS).toList();
        Assertions.assertTrue(users.isEmpty());
    }

    @Test
    public void preloadHasOne() {
        Users.create(new User(-1, "foo", true, User.Gender.MALE));
        Posts.create(new Post("first title", "contents", Users.firstOrNull()));
        Posts.create(new Post("title", "contents", Users.firstOrNull(), Posts.firstOrNull(), 10));

        List<Post> posts = Posts.all().preload(Posts.REPLY_FROM).toList();

        Assertions.assertEquals(posts.get(0).getReplyFrom().getTitle(), "title");
        Assertions.assertEquals(posts.get(1).getReplyFrom(), null);
    }

    @Test
    void hasOneParentRecordNotFound() {
        List<Post> posts = Posts.all().preload(Posts.REPLY_FROM).toList();
        Assertions.assertEquals(posts.size(), 0);
    }

    @Test
    public void preloadBelongsTo() {
        Users.create(new User(-1, "foo", true, User.Gender.MALE));
        Posts.create(new Post("first title", "contents", Users.firstOrNull()));
        List<Post> list = Posts.all().preload(Posts.OWNER).toList();
        Post post = list.get(0);
        Assertions.assertEquals(post.getUser().getName(), "foo");
    }

    @Test
    public void preloadParentRecordNotFound() {
        List<Post> list = Posts.all().preload(Posts.OWNER).toList();
        Assertions.assertEquals(list.size(), 0);
    }
}
