package net.orekyuu.moco.core.entity;

import net.orekyuu.moco.core.annotations.Column;
import net.orekyuu.moco.core.annotations.HasMany;
import net.orekyuu.moco.core.annotations.Table;

import java.util.ArrayList;
import java.util.List;

@Table(name = "users", immutable = true)
public class User {
    @Column(name = "id", generatedValue = true, unique = true)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "active")
    private boolean active;

    @HasMany(foreignKey = "user_id", key = "id")
    private List<Post> posts = new ArrayList<>();

    public User() {
    }

    public User(int id, String name, boolean active) {
        this.id = id;
        this.name = name;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public List<Post> getPosts() {
        return posts;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("id=").append(id);
        sb.append(", name='").append(name).append('\'');
        sb.append(", active=").append(active);
        sb.append('}');
        return sb.toString();
    }
}
