package net.orekyu.moco.core;

import net.orekyu.moco.core.entity.User;
import net.orekyu.moco.core.entity.Users;
import net.orekyuu.moco.core.RecordNotFoundException;
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

        User first = Users.first();
        Assertions.assertEquals(first.getName(), "foo");
    }

    @Test
    void firstNotFound() {
        Assertions.assertNull(Users.first());
    }

    @Test
    void findById() {
        Users.create(new User(-1, "foo", true));
        User first = Users.first();
        User byId = Users.findById(first.getId());
        Assertions.assertEquals(byId.getId(), first.getId());
        Assertions.assertEquals(byId.getName(), first.getName());
        Assertions.assertEquals(byId.isActive(), first.isActive());
    }

    @Test
    void findByIdNotFound() {
        Assertions.assertThrows(RecordNotFoundException.class, () -> Users.findById(1));
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
}
