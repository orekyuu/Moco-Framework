package net.orekyu.moco.core.entity;

import java.lang.Override;
import javax.annotation.Nonnull;
import net.orekyuu.moco.core.EntityList;
import net.orekyuu.moco.feeling.Select;

public final class UserList extends EntityList<UserList, User> {
  UserList(Select select) {
    super(select);
  }

  @Nonnull
  @Override
  public Select.QueryResultMapper<User> getMapper() {
    return Users.MAPPER;
  }
}
