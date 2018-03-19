package net.orekyu.moco.core.entity;

import net.orekyuu.moco.core.ConnectionManager;
import net.orekyuu.moco.core.attribute.BooleanAttribute;
import net.orekyuu.moco.core.attribute.IntAttribute;
import net.orekyuu.moco.core.attribute.StringAttribute;
import net.orekyuu.moco.feeling.Insert;
import net.orekyuu.moco.feeling.Select;
import net.orekyuu.moco.feeling.Table;
import net.orekyuu.moco.feeling.TableBuilder;
import net.orekyuu.moco.feeling.node.SqlBindParam;
import net.orekyuu.moco.feeling.node.SqlNodeArray;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Optional;

public final class Users {
  public static final Select.QueryResultMapper<User> MAPPER = new Select.QueryResultMapper<User>() {
    private Field id;

    private Field name;

    private Field active;

    {
      try {
        id = User.class.getDeclaredField("id");
        id.setAccessible(true);
        name = User.class.getDeclaredField("name");
        name.setAccessible(true);
        active = User.class.getDeclaredField("active");
        active.setAccessible(true);
      } catch (ReflectiveOperationException e) {
        throw new RuntimeException(e);
      }
    }

    @Override
    public User mapping(ResultSet resultSet) throws SQLException, ReflectiveOperationException {
      User record = new User();
      id.set(record, resultSet.getObject("id"));
      name.set(record, resultSet.getObject("name"));
      active.set(record, resultSet.getObject("active"));
      return record;
    }
  };

  public static final Table TABLE = new TableBuilder("users", MAPPER)._integer("id")._string("name")._boolean("active").build();

  public static final IntAttribute<User> ID = new IntAttribute<>(TABLE.intCol("id"), User::getId);

  public static final StringAttribute<User> NAME = new StringAttribute<>(TABLE.stringCol("name"), User::getName);

  public static final BooleanAttribute<User> ACTIVE = new BooleanAttribute<>(TABLE.booleanCol("active"), User::isActive);

  public static void create(@Nonnull User entity) {
    Insert insert = new Insert(TABLE);
    insert.setAttributes(Arrays.asList(NAME.ast(), ACTIVE.ast()));
    insert.setValues(new SqlNodeArray(Arrays.asList(new SqlBindParam(NAME.getAccessor().get(entity), NAME.bindType()), new SqlBindParam(ACTIVE.getAccessor().get(entity), ACTIVE.bindType()))));
    insert.executeQuery(ConnectionManager.getConnection());
  }

  @Nonnull
  public static UserList all() {
    return new UserList(TABLE.select());
  }

  @Nonnull
  public static Optional<User> first() {
    return all().limit(1).toList().stream().findFirst();
  }

  @Nullable
  public static User firstOrNull() {
    return first().orElse(null);
  }

  @Nonnull
  public static Optional<User> findById(@Nonnull int key) {
    return all().where(ID.eq(key)).limit(1).toList().stream().findFirst();
  }

  @Nullable
  public static User findOrNullById(@Nonnull int key) {
    return all().where(ID.eq(key)).limit(1).toList().stream().findFirst().orElse(null);
  }
}
