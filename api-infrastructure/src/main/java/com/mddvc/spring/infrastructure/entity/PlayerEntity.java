package com.mddvc.spring.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Table(name = "players",
        indexes =  @Index(name = "idx_player_name",
                columnList = "name", unique = true))
@Getter
public class PlayerEntity {
    @Column(name = "id") @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "player_id_generator")
    @SequenceGenerator(name = "player_id_generator",
            sequenceName = "player_id_seq",
            allocationSize = 10)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, length = 50)
    private String name;

    public PlayerEntity(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Player name cannot be null or empty");
        }
        if (name.trim().length() > 50) {
            throw new IllegalArgumentException("Player name cannot be longer than 50 characters");
        }
        this.name = name.trim();
    }

    protected PlayerEntity() {
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return  false;
        if (Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PlayerEntity that = (PlayerEntity) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return Hibernate.getClass(this).hashCode();
    }
}
