package net.orekyuu.moco.core;

import net.orekyuu.moco.core.entity.Point;
import net.orekyuu.moco.core.entity.Points;
import net.orekyuu.moco.core.entity.User;
import net.orekyuu.moco.core.entity.Users;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

public class InsertTest extends DatabaseTest {

    @Test
    void createUser() {
        Assertions.assertEquals(Users.all().toList().size(), 0);
        Users.create(new User(-1, "test", true, User.Gender.MALE));
        Assertions.assertEquals(Users.all().toList().size(), 1);
        User first = Users.firstOrNull();
        Assertions.assertNotEquals(first.getId(), -1);
        Assertions.assertEquals(first.getName(), "test");
        Assertions.assertEquals(first.isActive(), true);
        Assertions.assertEquals(first.getGender(), User.Gender.MALE);
    }

    @Test
    void createPoint() {
        Users.create(new User(-1, "test", true, User.Gender.MALE));
        User user = Users.firstOrNull();

        Assertions.assertEquals(Points.all().toList().size(), 0);
        Points.create(new Point(-1, user.getId(), BigDecimal.valueOf(100, 3)));

        List<User> users = Users.all().where(Users.ID.eq(user.getId())).preload(Users.USER_TO_POINTS).toList();
        User result = users.get(0);
        List<Point> points = result.getPoints();

        Assertions.assertEquals(points.size(), 1);
        Assertions.assertEquals(points.get(0).getAmount(), BigDecimal.valueOf(100, 3));
    }
}
