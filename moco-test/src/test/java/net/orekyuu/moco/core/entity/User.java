package net.orekyuu.moco.core.entity;

import net.orekyuu.moco.core.annotations.Column;
import net.orekyuu.moco.core.annotations.HasMany;
import net.orekyuu.moco.core.annotations.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Table(name = "users", immutable = true)
public class User {
    public enum Gender {
        MALE, FEMALE;
    }

    @Column(name = "id", generatedValue = true, unique = true)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "active")
    private boolean active;
    @Column(name = "gender")
    private Gender gender;
    @Column(name = "registered_at")
    private LocalDateTime registeredAt;

    @HasMany(foreignKey = "user_id", key = "id")
    private List<Post> posts = new ArrayList<>();

    public User() {
    }

    public User(int id, String name, boolean active, Gender gender) {
        this(id, name, active, gender, LocalDateTime.now());
    }

    public User(int id, String name, boolean active, Gender gender, LocalDateTime registeredAt) {
        this.id = id;
        this.name = name;
        this.active = active;
        this.gender = gender;
        this.registeredAt = registeredAt;
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

    public Gender getGender() {
        return gender;
    }

    public LocalDateTime getRegisteredAt() {
        return registeredAt;
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
        sb.append(", gender=").append(gender);
        sb.append(", registeredAt=").append(registeredAt);
        sb.append(", posts=").append(posts);
        sb.append('}');
        return sb.toString();
    }
}
