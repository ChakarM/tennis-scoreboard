package com.mddvc.spring.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "players",
        indexes =  @Index(name = "idx_player_name",
                columnList = "name", unique = true))
@Getter @Setter
public class PlayerEntity {
    @Column(name = "id") @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "player_id_generator")
    @SequenceGenerator(name = "player_id_generator",
            sequenceName = "player_id_seq",
            allocationSize = 10)
    private Long id;

    @Column(name = "name") @Getter
    private String name;

    public PlayerEntity(String name) {
        this.name = name;
    }

    public PlayerEntity() {
    }


}
