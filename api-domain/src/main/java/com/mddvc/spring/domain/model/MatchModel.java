package com.mddvc.spring.domain.model;

import com.mddvc.spring.domain.exception.MatchModelException;
import lombok.Getter;

import java.util.Objects;
import java.util.Optional;

public class MatchModel {
    @Getter
    private Long id;
    private final PlayerModel player1;
    private final PlayerModel player2;

    private int sets1 = 0;
    private int sets2 = 0;

    @Getter
    private SetModel currentSet = new SetModel();

    public MatchModel(PlayerModel player1, PlayerModel player2) {
        if (player1 == null || player2 == null) {
            throw new MatchModelException("Игроки не могут быть null");
        }
        if (player1.equals(player2)) {
            throw new MatchModelException("Имена игроков не могут совпадать");
        }
        this.player1 = player1;
        this.player2 = player2;
    }

    public MatchModel(PlayerModel playerModel1, PlayerModel player2, Long id) {
        this.player1 = playerModel1;
        this.player2 = player2;
        this.id = id;
    }

    public void scorePoint(int player) {
        if(isFinished()) return;
        currentSet.scorePoint(player);
        if (currentSet.isFinished()) {
            Integer setWinner = currentSet.getWinner().get();
            if (setWinner == 1) sets1++;
            else sets2++;

            if (!isFinished()) {
                currentSet = new SetModel();
            }
        }

    }

    public boolean isFinished() {
        return getWinner().isPresent();
    }

    public Optional<PlayerModel> getWinner(){
        if (sets1 == 2) return Optional.of(player1);
        if (sets2 == 2) return Optional.of(player2);
        return Optional.empty();
    }

    public int getSets(int player) {
        return player == 1 ? sets1 : sets2;
    }

    public PlayerModel getPlayer(int player) {
        return player == 1 ? player1 : player2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MatchModel that = (MatchModel) o;
        return Objects.equals(id, that.id) ||
                (Objects.equals(player1, that.player1) && Objects.equals(player2, that.player2));
    }

    @Override
    public int hashCode() {
        return Objects.hash(player1, player2);
    }

}
