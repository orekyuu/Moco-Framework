package net.orekyuu.moco.sample;

import net.orekyuu.moco.core.relation.HasManyRelation;

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

            // リレーション
            HasManyRelation<User, Post> posts = Users.POSTS;
            UserList userList = Users.all() // User全部持ってくる
                    .preload(posts); // 紐付いているPostも即時フェッチ
            // toListするとここで始めてクエリが実行される
            List<User> users = userList.toList();
            System.out.println(users);
        });
    }
}
