package net.orekyuu.moco.sample;

public class SimpleSelectSample extends SampleBase {
    public static void main(String[] args) {
        setup();
        transaction(() -> {
            Users.create(new User(-1, "hoge", true));
            Users.create(new User(-1, "fuga", true));
            Users.create(new User(-1, "piyo", false));

            // find first user
            User first = Users.firstOrNull();
            System.out.println(first);
            // find by id
            assert first != null;
            System.out.println(Users.findById(first.getId()));
            // find all users
            System.out.println(Users.all().toList());
            // find activated user
            System.out.println(Users.all().where(Users.ACTIVE.isTrue()).toList());
            // find hoge
            System.out.println(Users.all().where(Users.NAME.eq("hoge")).toList());
            // find fuga and activated
            System.out.println(Users.all().where(Users.NAME.eq("fuga"), Users.ACTIVE.isTrue()).toList());
        });
    }
}
