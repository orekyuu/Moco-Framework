package net.orekyu.moco.core.entity;

import net.orekyuu.moco.core.ConnectionManager;
import net.orekyuu.moco.core.EntityList;
import net.orekyuu.moco.feeling.Select;

import java.util.List;

public class UserList extends EntityList<UserList> {

    public UserList(Select select) {
        super(select);
    }

    public List<User> toList() {
        return select.executeQuery(ConnectionManager.getConnection(), Users.MAPPER);
    }
}
