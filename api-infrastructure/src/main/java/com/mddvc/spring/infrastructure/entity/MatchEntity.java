package com.mddvc.spring.infrastructure.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "matches",
        indexes = {
        @Index(name = "idx_player1", columnList = "player1"),
        @Index(name = "idx_player2", columnList = "player2")},
        check = @CheckConstraint(name = "check_match_players_and_winner",
                constraint = "player1 <> player2 AND (winner = player1 OR winner = player2)")
)

public class MatchEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "match_id_gen")
    @SequenceGenerator(name = "match_id_gen",
            sequenceName = "match_id_seq",
            allocationSize = 10)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "player1", referencedColumnName = "id", nullable = false)
    private PlayerEntity playerEntity1;

    @ManyToOne(optional = false)
    @JoinColumn(name = "player2", referencedColumnName = "id", nullable = false)
    private PlayerEntity playerEntity2;

    @Getter
    @ManyToOne(optional = false)
    @JoinColumn(name = "winner", referencedColumnName = "id", nullable = false)
    private PlayerEntity winner;

    public MatchEntity(PlayerEntity playerEntity1, PlayerEntity playerEntity2, PlayerEntity winner) {
        validatePlayers(playerEntity1, playerEntity2, winner);
        this.playerEntity1 = playerEntity1;
        this.playerEntity2 = playerEntity2;
        this.winner = winner;
    }

    private void validatePlayers(PlayerEntity playerEntity1, PlayerEntity playerEntity2, PlayerEntity winner) {
        if (playerEntity1 == null || playerEntity2 == null || winner == null) {
            throw new IllegalArgumentException("Players and winner can't be null");
        }
        if (playerEntity1.equals(playerEntity2)){
            throw new IllegalArgumentException("Players must be different");
        }
        if (!winner.equals(playerEntity1) && !winner.equals(playerEntity2)) {
            throw new IllegalArgumentException("Winner must be one of the players");
        }
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
