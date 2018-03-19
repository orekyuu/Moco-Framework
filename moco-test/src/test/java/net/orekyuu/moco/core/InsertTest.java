package net.orekyuu.moco.core;

import net.orekyuu.moco.core.entity.User;
import net.orekyuu.moco.core.entity.Users;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InsertTest extends DatabaseTest {

    @Test
    void createUser() {
        Assertions.assertEquals(Users.all().toList().size(), 0);
        Users.create(new User(-1, "test", true));
        Assertions.assertEquals(Users.all().toList().size(), 1);
        User first = Users.firstOrNull();
        Assertions.assertNotEquals(first.getId(), -1);
        Assertions.assertEquals(first.getName(), "test");
        Assertions.assertEquals(first.isActive(), true);
    }
}
