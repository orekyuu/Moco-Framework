package net.orekyuu.moco.sample;

public class InsertSample extends SampleBase {
    public static void main(String[] args) {
        setup();
        transaction(() -> {
            User user = new User("user1", true);
            Users.create(user);
        });
    }
}
