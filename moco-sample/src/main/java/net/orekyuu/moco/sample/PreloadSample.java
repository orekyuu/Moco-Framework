package net.orekyuu.moco.sample;

import java.util.List;

public class PreloadSample extends SampleBase {
    public static void main(String[] args) {
        setup();
        transaction(() -> {
            for (int i = 0; i < 10; i++) {
                Users.create(new User(-1, "user1", true));
            }
            for (User user : Users.all().toList()) {
                Posts.create(new Post(-1, user.getId(), "post1"));
                Posts.create(new Post(-1, user.getId(), "post2"));
                Posts.create(new Post(-1, user.getId(), "post3"));
            }

            List<Post> toList = Posts.all().preload(Posts.USERS).toList();
        });
    }
}
