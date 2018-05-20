package net.orekyuu.moco.core;

import net.orekyuu.moco.core.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UpdateTest extends DatabaseTest {

    private User testUser1;
    private User testUser2;
    private Post post1;

    @BeforeEach
    void createEntity() {
        Users.create(new User(-1, "test_user1", true, User.Gender.MALE));
        Users.create(new User(-1, "test_user2", true, User.Gender.MALE));

        testUser1 = Users.all().where(Users.NAME.eq("test_user1")).firstOrNull();
        testUser2 = Users.all().where(Users.NAME.eq("test_user2")).firstOrNull();

        Posts.create(new Post("post1", "post_content1", testUser1, null, 10));
        post1 = Posts.all().where(Posts.TITLE.eq("post1")).firstOrNull();
    }

    @Test
    void updateAllUser() {
        Users.all().update(Users.NAME.set("updated_user_name"));

        Assertions.assertEquals(Users.all().where(Users.NAME.eq("updated_user_name")).toList().size(), 2);
    }

    @Test
    void updateUser1() {
        Users.all().where(Users.NAME.eq(testUser1.getName())).update(Users.ACTIVE.set(false));

        Assertions.assertFalse(Users.findOrNullById(testUser1.getId()).isActive());
        Assertions.assertTrue(Users.findOrNullById(testUser2.getId()).isActive());
    }

    @Test
    void updateActiveAndGender() {
        Users.all()
                .where(Users.NAME.eq(testUser1.getName()))
                .update(Users.ACTIVE.set(false), Users.GENDER.set(User.Gender.FEMALE));

        Assertions.assertFalse(Users.findOrNullById(testUser1.getId()).isActive());
        Assertions.assertEquals(Users.findOrNullById(testUser1.getId()).getGender(), User.Gender.FEMALE);

        Assertions.assertTrue(Users.findOrNullById(testUser2.getId()).isActive());
        Assertions.assertEquals(Users.findOrNullById(testUser2.getId()).getGender(), User.Gender.MALE);
    }

    @Test
    void updateRelation() {
        PostList postList = Posts.all().where(Posts.ID.eq(post1.getId()));
        postList.update(Posts.USER_ID.set(testUser2.getId()));

        Post post = postList.preload(Posts.OWNER).firstOrNull();
        Assertions.assertEquals(post.getUser().getId(), testUser2.getId());
    }
}
