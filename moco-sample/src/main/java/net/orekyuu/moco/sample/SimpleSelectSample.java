package net.orekyuu.moco.sample;

import java.util.IntSummaryStatistics;

public class SimpleSelectSample extends SampleBase {
    public static void main(String[] args) {
        setup();
        transaction(() -> {
            Users.create(new User("hoge", true));
            Users.create(new User("fuga", true));
            Users.create(new User("piyo", false));

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
            // order by and limit
            System.out.println(Users.all().order(Users.ID.desc()).limit(1).toList());
            // stream fetch
            for (int i = 0; i < 100; i++) {
                Users.create(new User(String.valueOf(i), false));
            }
            IntSummaryStatistics statistics = Users.all().where(Users.ACTIVE.isFalse()).stream(10)
                    .map(User::getName)
                    .filter(str -> str.matches("\\d+"))
                    .mapToInt(Integer::valueOf).summaryStatistics();
            System.out.println(statistics.getAverage());
            System.out.println(statistics.getCount());
            System.out.println(statistics.getMax());
            System.out.println(statistics.getMin());
            System.out.println(statistics.getSum());
        });
    }
}
