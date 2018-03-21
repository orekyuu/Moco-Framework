package net.orekyuu.moco.core.entity;

import net.orekyuu.moco.core.annotations.Column;
import net.orekyuu.moco.core.annotations.HasOne;
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
    @Column(name = "reply_to")
    private int replyTo;

    @HasOne(key = "id", foreignKey = "reply_to")
    private Post replyFrom;

    public Post() {
    }

    public Post(String title, String contents, User user) {
        this(title, contents, user, null);
    }

    public Post(String title, String contents, User user, Post post) {
        this.title = title;
        this.contents = contents;
        this.userId = user.getId();
        if (post != null) {
            this.replyTo = post.id;
        }
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

    public int getReplyTo() {
        return replyTo;
    }

    public Post getReplyFrom() {
        return replyFrom;
    }
}
