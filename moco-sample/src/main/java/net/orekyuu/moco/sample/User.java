package net.orekyuu.moco.sample;

import net.orekyuu.moco.core.annotations.Column;
import net.orekyuu.moco.core.annotations.HasMany;
import net.orekyuu.moco.core.annotations.Table;

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

    @HasMany(key = "id", foreignKey = "user_id")
    private List<Post> posts = new ArrayList<>();

    public User() {
    }

    public User(String name, boolean active) {
        this(name, active, Gender.MALE);
    }

    public User(String name, boolean active, Gender gender) {
        this.name = name;
        this.active = active;
        this.gender = gender;
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
        sb.append('}');
        return sb.toString();
    }
}
