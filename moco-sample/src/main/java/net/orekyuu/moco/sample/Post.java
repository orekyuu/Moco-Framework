package net.orekyuu.moco.sample;

import net.orekyuu.moco.core.annotations.Column;
import net.orekyuu.moco.core.annotations.Table;

@Table(name = "posts", immutable = true)
public class Post {
    private int id;
    private int userId;
    private String title;

    public Post(
            @Column(name = "id", generatedValue = true) int id,
            @Column(name = "user_id") int userId,
            @Column(name = "title") String title) {
        this.id = id;
        this.userId = userId;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Post{");
        sb.append("id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", title='").append(title).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
