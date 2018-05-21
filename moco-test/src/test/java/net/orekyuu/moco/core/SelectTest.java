package net.orekyuu.moco.core;

import net.orekyuu.moco.core.entity.User;
import net.orekyuu.moco.core.entity.Users;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class SelectTest extends DatabaseTest {

    @Test
    void findAll() {
        Users.create(new User(-1, "foo", true, User.Gender.MALE));
        Users.create(new User(-1, "foo", false, User.Gender.MALE));
        Users.create(new User(-1, "bar", false, User.Gender.MALE));

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
        Users.create(new User(-1, "foo", false, User.Gender.MALE));
        Users.create(new User(-1, "bar", false, User.Gender.MALE));

        User first = Users.firstOrNull();
        Assertions.assertEquals(first.getName(), "foo");
    }

    @Test
    void findByRegisteredAt() {
        Users.create(new User(-1, "foo", true, User.Gender.MALE, LocalDateTime.of(2018, 5, 20, 0, 0)));
        Users.create(new User(-1, "bar", true, User.Gender.MALE, LocalDateTime.of(2018, 5, 21, 0, 0)));
        Users.create(new User(-1, "baz", true, User.Gender.MALE, LocalDateTime.of(2018, 5, 22, 0, 0)));

        List<User> after = Users.all().where(Users.REGISTERED_AT.after(LocalDateTime.of(2018, 5, 21, 0, 0))).toList();
        Assertions.assertEquals(after.size(), 1);
        Assertions.assertEquals(after.get(0).getName(), "baz");

        List<User> before = Users.all().where(Users.REGISTERED_AT.before(LocalDateTime.of(2018, 5, 21, 0, 0))).toList();
        Assertions.assertEquals(before.size(), 1);
        Assertions.assertEquals(before.get(0).getName(), "foo");

        List<User> between = Users.all()
                .where(Users.REGISTERED_AT.between(LocalDate.of(2018, 5, 20).atStartOfDay(), LocalDate.of(2018, 5, 21).atStartOfDay()))
                .order(Users.ID.asc())
                .toList();
        Assertions.assertEquals(between.size(), 2);
        Assertions.assertEquals(between.get(0).getName(), "foo");
        Assertions.assertEquals(between.get(1).getName(), "bar");

        List<User> allDay = Users.all().where(Users.REGISTERED_AT.allDay(LocalDate.of(2018, 5, 21))).toList();
        Assertions.assertEquals(allDay.size(), 1);
        Assertions.assertEquals(allDay.get(0).getName(), "bar");
    }

    @Test
    void firstNotFound() {
        Assertions.assertNull(Users.firstOrNull());
        Assertions.assertFalse(Users.first().isPresent());
    }

    @Test
    void findById() {
        Users.create(new User(-1, "foo", true, User.Gender.MALE));
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
        Users.create(new User(-1, "bar", false, User.Gender.MALE));
        Users.create(new User(-1, "foo", true, User.Gender.MALE));
        Users.create(new User(-1, "foo", false, User.Gender.MALE));

        List<User> users = Users.all().where(Users.NAME.eq("foo")).toList();
        Assertions.assertEquals(users.size(), 2);
        for (User user : users) {
            Assertions.assertEquals(user.getName(), "foo");
        }
    }

    @Test
    void findByActive() {
        Users.create(new User(-1, "bar", true, User.Gender.MALE));
        Users.create(new User(-1, "foo", false, User.Gender.MALE));
        Users.create(new User(-1, "foo", true, User.Gender.MALE));

        List<User> users = Users.all().where(Users.ACTIVE.isTrue()).toList();
        Assertions.assertEquals(users.size(), 2);
        for (User user : users) {
            Assertions.assertTrue(user.isActive());
        }
    }

    @Test
    void findFooAndActive() {
        Users.create(new User(-1, "bar", true, User.Gender.MALE));
        Users.create(new User(-1, "foo", false, User.Gender.MALE));
        Users.create(new User(-1, "foo", true, User.Gender.MALE));

        List<User> users = Users.all().where(Users.NAME.eq("foo"), Users.ACTIVE.isTrue()).toList();
        Assertions.assertEquals(users.size(), 1);
        Assertions.assertEquals(users.get(0).getName(), "foo");
        Assertions.assertEquals(users.get(0).isActive(), true);
    }

    @Test
    void findFooAndActiveMultiWhere() {
        Users.create(new User(-1, "bar", true, User.Gender.MALE));
        Users.create(new User(-1, "foo", false, User.Gender.MALE));
        Users.create(new User(-1, "foo", true, User.Gender.MALE));

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
        Users.create(new User(-1, "bar", true, User.Gender.MALE));
        Users.create(new User(-1, "foo", false, User.Gender.MALE));
        Users.create(new User(-1, "foo", true, User.Gender.MALE));

        List<User> users = Users.all().where(Users.NAME.eq(Users.NAME)).toList();
        Assertions.assertEquals(users.size(), 3);
    }

    @Test
    void limitAndOffset() {
        Users.create(new User(-1, "user1", true, User.Gender.MALE));
        Users.create(new User(-1, "user2", true, User.Gender.MALE));
        Users.create(new User(-1, "user3", true, User.Gender.MALE));
        List<User> users = Users.all().limitAndOffset(1, 1).toList();
        Assertions.assertEquals(users.size(), 1);
        Assertions.assertEquals(users.get(0).getName(), "user2");
    }

    @Test
    void limitOnly() {
        Users.create(new User(-1, "user1", true, User.Gender.MALE));
        Users.create(new User(-1, "user2", true, User.Gender.MALE));
        Users.create(new User(-1, "user3", true, User.Gender.MALE));
        List<User> users = Users.all().limit(2).toList();
        Assertions.assertEquals(users.size(), 2);
        Assertions.assertEquals(users.get(0).getName(), "user1");
        Assertions.assertEquals(users.get(1).getName(), "user2");
    }

    @Test
    void order() {
        Users.create(new User(-1, "user1", true, User.Gender.MALE));
        Users.create(new User(-1, "user2", false, User.Gender.MALE));
        Users.create(new User(-1, "user3", true, User.Gender.MALE));

        List<User> desc = Users.all().order(Users.ID.desc()).toList();
        Assertions.assertEquals(desc.size(), 3);
        Assertions.assertEquals(desc.get(0).getName(), "user3");

        List<User> asc = Users.all().order(Users.ID.asc()).toList();
        Assertions.assertEquals(asc.size(), 3);
        Assertions.assertEquals(asc.get(0).getName(), "user1");
    }

    @Test
    void order2() {
        Users.create(new User(-1, "user1", true, User.Gender.MALE));
        Users.create(new User(-1, "user2", true, User.Gender.MALE));
        Users.create(new User(-1, "user3", false,User.Gender.MALE));

        List<User> desc = Users.all().order(Users.ACTIVE.desc(), Users.ID.desc()).toList();
        Assertions.assertEquals(desc.size(), 3);
        Assertions.assertEquals(desc.get(0).getName(), "user2");
    }
}
