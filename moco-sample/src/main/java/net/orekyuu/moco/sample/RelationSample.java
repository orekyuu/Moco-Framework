package net.orekyuu.moco.sample;

import java.util.List;

public class RelationSample extends SampleBase {

    public static void main(String[] args) {
        setup();
        transaction(() -> {
            Users.create(new User("hoge", true));
            Posts.create(new Post("title1", "content", Users.firstOrNull()));
            Posts.create(new Post("title2", "content", Users.firstOrNull()));

            // Query:
            // select `users`.`id` , `users`.`name` , `users`.`active` from users where `users`.`name` = ?
            // select `posts`.`contents` , `posts`.`user_id` , `posts`.`id` , `posts`.`title` from posts where `posts`.`user_id` in (? )
            List<User> users = Users.all()
                    .where(Users.NAME.eq("hoge"))
                    .preload(Users.USER_TO_POSTS)
                    .toList();
            for (User user : users) {
                System.out.println("user: " + user.getName());
                for (Post post : user.getPosts()) {
                    System.out.println("  post: " + post.getTitle());
                }
            }
        });
    }
}
