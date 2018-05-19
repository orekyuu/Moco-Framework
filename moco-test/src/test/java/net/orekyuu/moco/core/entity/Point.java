package net.orekyuu.moco.core.entity;

import net.orekyuu.moco.core.annotations.BelongsTo;
import net.orekyuu.moco.core.annotations.Column;
import net.orekyuu.moco.core.annotations.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Table(name = "points")
public class Point {
    @Column(name = "id", generatedValue = true, unique = true)
    private int id;
    @Column(name = "user_id")
    private int userId;
    @Column(name = "amount")
    private BigDecimal amount;
    @Column(name = "occurred_at")
    private LocalDateTime occurredAt;

    @BelongsTo(key = "user_id", foreignKey = "id")
    private User user;

    public Point() {
    }

    public Point(int id, int userId, BigDecimal amount) {
        this.id = id;
        this.userId = userId;
        this.amount = amount;
        this.occurredAt = LocalDateTime.now();
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDateTime getOccurredAt() {
        return occurredAt;
    }

    public User getUser() {
        return user;
    }
}
