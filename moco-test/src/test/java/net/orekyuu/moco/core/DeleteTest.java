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
            Users.create(new User(-1, "foo", true));
            Users.create(new User(-1, "foo", false));
            Users.create(new User(-1, "bar", false));
        }

        @Test
        public void deleteAll() {
            Users.all().delete();
            Assertions.assertEquals(Users.all().toList().size(), 0);
        }

        @Test
        public void deleteWhere() {
            Users.all().where(Users.ACTIVE.isFalse()).delete();
            List<User> users = Users.all().toList();
            Assertions.assertEquals(users.size(), 1);
            Assertions.assertEquals(users.get(0).getName(), "foo");
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
