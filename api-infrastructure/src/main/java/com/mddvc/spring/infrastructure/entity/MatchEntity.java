package com.mddvc.spring.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;

//10:50 -
@Entity
@Table(name = "matches",
        indexes = {
        @Index(name = "idx_player1", columnList = "player1"),
        @Index(name = "idx_player2", columnList = "player2")})
public class MatchEntity {
    @Getter
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "match_id_gen")
    @SequenceGenerator(name = "match_id_gen",
            sequenceName = "match_id_seq",
            allocationSize = 10)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "player1", referencedColumnName = "id", nullable = false)
    private PlayerEntity playerEntity1;

    @ManyToOne
    @JoinColumn(name = "player2", referencedColumnName = "id", nullable = false)
    private PlayerEntity playerEntity2;

    @Getter
    @ManyToOne
    @JoinColumn(name = "winner", referencedColumnName = "id", nullable = false)
    private PlayerEntity winner;

    public MatchEntity(PlayerEntity playerEntity1, PlayerEntity playerEntity2, PlayerEntity winner) {
        this.playerEntity1 = playerEntity1;
        this.playerEntity2 = playerEntity2;
        this.winner = winner;
    }

    protected MatchEntity() {
    }

    public PlayerEntity getPlayer1() {
        return playerEntity1;
    }

    public PlayerEntity getPlayer2() {
        return playerEntity2;
    }

}
