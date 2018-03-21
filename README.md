# Moco-Framework
Java向けのORMです。

# 使い方

Entityクラスを作る
```java
@Table(name = "users")
public class User {
    @Column(name = "id", generatedValue = true, unique = true)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "active")
    private boolean active;

    // getter and constructor
    ...
}
```

コンパイルするとUsers/UserListが生成されます。生成されたクラスを使ってクエリを作ります。

## Insert
```java
Users.create(new User("name", true));
```

## Select
```java
UserList userList = Users.all();
userList.where(Users.NAME.eq("orekyuu"));
List<User> users = userList.toList();
```

## n+1の解決
preloadを使うことで複数回クエリを投げて解決します。

Entityクラス
```java
@Table(name = "users")
public class User {
    @Column(name = "id", generatedValue = true, unique = true)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "active")
    private boolean active;

    @HasMany(foreignKey = "id", targetKey = "user_id")
    private List<Post> posts = new ArrayList<>();
    ...
}
```

検索
```java
// Query:
// select `users`.`id` , `users`.`name` , `users`.`active` from users where `users`.`name` = ?
// select `posts`.`contents` , `posts`.`user_id` , `posts`.`id` , `posts`.`title` from posts where `posts`.`user_id` in (? )
List<User> users = Users.all()
    .where(Users.NAME.eq("hoge"))
    .preload(Users.USER_TO_POSTS)
    .toList();
```

# プロジェクト構成
|モジュール||
|--|--|
|feeling|Mocoで使用するクエリビルダです|
|moco|Moco本体のモジュールで、クエリの実行にはfeelingを使います|
|chou|コードを生成するためのプロセッサを提供するモジュールです|
|moco-sample|Mocoの使用例を示すためのサンプルプロジェクトです|
|moco-test|Mocoのテスト用のモジュールです|
