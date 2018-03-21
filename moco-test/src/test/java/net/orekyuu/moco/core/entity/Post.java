package net.orekyuu.moco.core.entity;

import net.orekyuu.moco.core.annotations.Column;
import net.orekyuu.moco.core.annotations.Table;

@Table(name = "posts")
public class Post {
    @Column(name = "id", generatedValue = true, unique = true)
    private int id;
    @Column(name = "title")
    private String title;
    @Column(name = "contents")
    private String contents;
    @Column(name = "user_id")
    private int userId;

    public Post() {
    }

    public Post(int id, String title, String contents, User user) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.userId = user.getId();
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContents() {
        return contents;
    }

    public int getUserId() {
        return userId;
    }
}
