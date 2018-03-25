package net.orekyuu.moco.core;

import net.orekyuu.moco.core.entity.User;
import net.orekyuu.moco.core.entity.Users;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SelectTest extends DatabaseTest {

    @Test
    void findAll() {
        Users.create(new User(-1, "foo", true));
        Users.create(new User(-1, "foo", false));
        Users.create(new User(-1, "bar", false));

        List<User> users = Users.all().toList();
        Assertions.assertEquals(users.size(), 3);
    }

    @Test
    void findAllEmpty() {
        List<User> users = Users.all().toList();
        Assertions.assertEquals(users.size(), 0);
    }

    @Test
    void first() {
        Users.create(new User(-1, "foo", false));
        Users.create(new User(-1, "bar", false));

        User first = Users.firstOrNull();
        Assertions.assertEquals(first.getName(), "foo");
    }

    @Test
    void firstNotFound() {
        Assertions.assertNull(Users.firstOrNull());
        Assertions.assertFalse(Users.first().isPresent());
    }

    @Test
    void findById() {
        Users.create(new User(-1, "foo", true));
        User first = Users.firstOrNull();
        User byId = Users.findOrNullById(first.getId());
        Assertions.assertEquals(byId.getId(), first.getId());
        Assertions.assertEquals(byId.getName(), first.getName());
        Assertions.assertEquals(byId.isActive(), first.isActive());
    }

    @Test
    void findByIdNotFound() {
        Assertions.assertEquals(Users.findOrNullById(1), null);
        Assertions.assertFalse(Users.findById(1).isPresent());
    }

    @Test
    void findByName() {
        Users.create(new User(-1, "bar", false));
        Users.create(new User(-1, "foo", true));
        Users.create(new User(-1, "foo", false));

        List<User> users = Users.all().where(Users.NAME.eq("foo")).toList();
        Assertions.assertEquals(users.size(), 2);
        for (User user : users) {
            Assertions.assertEquals(user.getName(), "foo");
        }
    }

    @Test
    void findByActive() {
        Users.create(new User(-1, "bar", true));
        Users.create(new User(-1, "foo", false));
        Users.create(new User(-1, "foo", true));

        List<User> users = Users.all().where(Users.ACTIVE.isTrue()).toList();
        Assertions.assertEquals(users.size(), 2);
        for (User user : users) {
            Assertions.assertTrue(user.isActive());
        }
    }

    @Test
    void findFooAndActive() {
        Users.create(new User(-1, "bar", true));
        Users.create(new User(-1, "foo", false));
        Users.create(new User(-1, "foo", true));

        List<User> users = Users.all().where(Users.NAME.eq("foo"), Users.ACTIVE.isTrue()).toList();
        Assertions.assertEquals(users.size(), 1);
        Assertions.assertEquals(users.get(0).getName(), "foo");
        Assertions.assertEquals(users.get(0).isActive(), true);
    }

    @Test
    void findFooAndActiveMultiWhere() {
        Users.create(new User(-1, "bar", true));
        Users.create(new User(-1, "foo", false));
        Users.create(new User(-1, "foo", true));

        List<User> users = Users.all()
                .where(Users.NAME.eq("foo"))
                .where(Users.ACTIVE.isTrue())
                .toList();
        Assertions.assertEquals(users.size(), 1);
        Assertions.assertEquals(users.get(0).getName(), "foo");
        Assertions.assertEquals(users.get(0).isActive(), true);
    }

    @Test
    void findAttributeEq() {
        Users.create(new User(-1, "bar", true));
        Users.create(new User(-1, "foo", false));
        Users.create(new User(-1, "foo", true));

        List<User> users = Users.all().where(Users.NAME.eq(Users.NAME)).toList();
        Assertions.assertEquals(users.size(), 3);
    }

    @Test
    void limitAndOffset() {
        Users.create(new User(-1, "user1", true));
        Users.create(new User(-1, "user2", true));
        Users.create(new User(-1, "user3", true));
        List<User> users = Users.all().limitAndOffset(1, 1).toList();
        Assertions.assertEquals(users.size(), 1);
        Assertions.assertEquals(users.get(0).getName(), "user2");
    }

    @Test
    void limitOnly() {
        Users.create(new User(-1, "user1", true));
        Users.create(new User(-1, "user2", true));
        Users.create(new User(-1, "user3", true));
        List<User> users = Users.all().limit(2).toList();
        Assertions.assertEquals(users.size(), 2);
        Assertions.assertEquals(users.get(0).getName(), "user1");
        Assertions.assertEquals(users.get(1).getName(), "user2");
    }

    @Test
    void order() {
        Users.create(new User(-1, "user1", true));
        Users.create(new User(-1, "user2", false));
        Users.create(new User(-1, "user3", true));

        List<User> desc = Users.all().order(Users.ID.desc()).toList();
        Assertions.assertEquals(desc.size(), 3);
        Assertions.assertEquals(desc.get(0).getName(), "user3");

        List<User> asc = Users.all().order(Users.ID.asc()).toList();
        Assertions.assertEquals(asc.size(), 3);
        Assertions.assertEquals(asc.get(0).getName(), "user1");
    }

    @Test
    void order2() {
        Users.create(new User(-1, "user1", true));
        Users.create(new User(-1, "user2", true));
        Users.create(new User(-1, "user3", false));

        List<User> desc = Users.all().order(Users.ACTIVE.desc(), Users.ID.desc()).toList();
        Assertions.assertEquals(desc.size(), 3);
        Assertions.assertEquals(desc.get(0).getName(), "user2");
    }
}
