package net.orekyuu.moco.core;

import net.orekyuu.moco.core.entity.User;
import net.orekyuu.moco.core.entity.Users;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DeleteTest extends DatabaseTest {

    @Nested
    class NotEmpty {
        @BeforeEach
        public void setup() {
            Users.create(new User(-1, "foo", true, User.Gender.MALE));
            Users.create(new User(-1, "foo", false, User.Gender.MALE));
            Users.create(new User(-1, "bar", false, User.Gender.MALE));
        }

        @Test
        public void deleteAll() {
            Users.all().delete();
            Assertions.assertEquals(Users.all().toList().size(), 0);
        }

        @Test
        public void limit() {
            Users.all().where(Users.ACTIVE.isFalse()).limit(1).delete();
            List<User> users = Users.all().where(Users.ACTIVE.isFalse()).toList();
            Assertions.assertEquals(users.size(), 1);
            Assertions.assertEquals(users.get(0).getName(), "bar");
        }

        @Test
        public void limitAndOffset() {
            Assertions.assertThrows(UnsupportedOperationException.class, () -> Users.all().where(Users.ACTIVE.isFalse()).limitAndOffset(1, 1).delete());
            List<User> users = Users.all().toList();
            Assertions.assertEquals(users.size(), 3);
        }

        @Test
        public void deleteWhere() {
            Users.all().where(Users.ACTIVE.isFalse()).delete();
            List<User> users = Users.all().toList();
            Assertions.assertEquals(users.size(), 1);
            Assertions.assertEquals(users.get(0).getName(), "foo");
        }

        @Test
        public void orderBy() {
            Users.all().order(Users.ID.desc()).limit(1).delete();
            List<User> users = Users.all().toList();
            Assertions.assertEquals(users.size(), 2);
            Assertions.assertEquals(users.get(0).getName(), "foo");
            Assertions.assertEquals(users.get(0).isActive(), true);

            Assertions.assertEquals(users.get(1).getName(), "foo");
            Assertions.assertEquals(users.get(1).isActive(), false);
        }
    }

    @Nested
    class Empty {
        @Test
        public void deleteAll() {
            Users.all().delete();
            Assertions.assertEquals(Users.all().toList().size(), 0);
        }

        @Test
        public void deleteWhere() {
            Users.all().where(Users.ACTIVE.isFalse()).delete();
            Assertions.assertEquals(Users.all().toList().size(), 0);
        }
    }
}
