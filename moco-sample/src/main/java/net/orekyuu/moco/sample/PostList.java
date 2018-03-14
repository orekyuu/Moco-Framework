package net.orekyuu.moco.sample;

import net.orekyuu.moco.core.EntityList;
import net.orekyuu.moco.feeling.Select;

public class PostList extends EntityList<PostList, Post> {

    public PostList(Select select) {
        super(select);
    }

    @Override
    public Select.QueryResultMapper<Post> getMapper() {
        return Posts.MAPPER;
    }
}
