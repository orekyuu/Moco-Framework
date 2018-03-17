package net.orekyuu.moco.sample;

import net.orekyuu.moco.core.EntityList;
import net.orekyuu.moco.feeling.Select;

public class UserList extends EntityList<UserList, User> {

    public UserList(Select select) {
        super(select);
    }

    @Override
    public Select.QueryResultMapper<User> getMapper() {
        return Users.MAPPER;
    }
}
