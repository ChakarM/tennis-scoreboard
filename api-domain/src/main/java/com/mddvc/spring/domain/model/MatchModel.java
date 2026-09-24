package com.mddvc.spring.domain.model;

import com.mddvc.spring.domain.exception.MatchModelException;
import lombok.Getter;

import java.util.Objects;

public class MatchModel {
    private Long id;
    private final PlayerModel playerModel1;
    private final PlayerModel playerModel2;

    private Integer points1 = 0;
    private Integer points2 = 0;

    private int games1;
    private int games2;

    private int sets1;
    private int sets2;

    private boolean isTieBreak = false;

    private Integer tieBreakPoints1;
    private Integer tieBreakPoints2;

    @Getter
    private PlayerModel winner = null;

    public MatchModel(PlayerModel playerModel1, PlayerModel playerModel2) {
        if (playerModel1.equals(playerModel2)) {
            throw new MatchModelException("Имена игроков не могут совпадать");
        }
        this.playerModel1 = playerModel1;
        this.playerModel2 = playerModel2;
    }

    public MatchModel(PlayerModel playerModel1, PlayerModel playerModel2, Long id) {
        this.playerModel1 = playerModel1;
        this.playerModel2 = playerModel2;
        this.id = id;
    }

    public MatchModel(PlayerModel playerModel1, PlayerModel playerModel2, PlayerModel winner) {
        this.playerModel1 = playerModel1;
        this.playerModel2 = playerModel2;
        this.winner = winner;
    }

    public void pointWonBy(int player) {
        if (winner != null) return;
        if (isTieBreak) {
            scoreTieBreak(player);
        } else {
            scoreGamePoint(player);

        }
        checkGameWinner();
        checkSetWinner();
        checkMatchWinner();
    }

    private void scoreTieBreak(int player) {
        if (player == 1) tieBreakPoints1++;
        else if (player == 2) {
            tieBreakPoints2++;
        }
    }

    private void checkSetWinner() {
        if (games1 == 6 && games2 == 6 && !isTieBreak) {
            isTieBreak = true;
            resetPoints();
            tieBreakPoints1 = 0;
            tieBreakPoints2 = 0;
            return;
        }
        if (isTieBreak) return;
        boolean player1won = games1 >= 6 && games1 - games2 >= 2;
        boolean player2won = games2 >= 6 && games2 - games1 >= 2;
        if (player1won) {
            sets1++;
            resetSet();
        } else if (player2won) {
            sets2++;
            resetSet();
        }
    }

    private void resetSet() {
        games1 = 0;
        games2 = 0;
        isTieBreak = false;
        resetPoints();
        resetTieBreakPoints();
    }

    private void checkTieBreakWinner() {
        boolean player1Won = tieBreakPoints1 >= 7 && tieBreakPoints1 - tieBreakPoints2 >= 2;
        boolean player2Won = tieBreakPoints2 >= 7 && tieBreakPoints2 - tieBreakPoints1 >= 2;

        if (player1Won) {
            sets1++;
            resetTieBreak();
        } else if (player2Won) {
            sets2++;
            resetTieBreak();
        }
    }

    private void checkMatchWinner() {
        if (sets1 == 2) winner = playerModel1;
        else if (sets2 == 2) winner = playerModel2;
    }

    private void checkGameWinner() {
        if (isTieBreak) {
            checkTieBreakWinner();
            return;
        }

        boolean player1won = points1 >= 4 && points1 - points2 >= 2;
        boolean player2won = points2 >= 4 && points2 - points1 >= 2;

        if (player1won) {
            games1++;
            resetPoints();
        } else if (player2won) {
            games2++;
            resetPoints();
        }

    }

    private void scoreGamePoint(int player) {
        if (player == 1) {
            points1++;
        } else if (player == 2) {
            points2++;
        }
        if (points1 == 4 && points2 == 4) {
            points1 = 3;
            points2 = 3;
        }
    }

    private void resetPoints() {
        points1 = 0;
        points2 = 0;
    }

    private void resetTieBreakPoints() {
        tieBreakPoints1 = null;
        tieBreakPoints2 = null;
    }

    private void resetTieBreak() {
        isTieBreak = false;
        games1 = 0;
        games2 = 0;
        resetTieBreakPoints();
        resetPoints();
    }

    public int getGames(int player) {
        return player == 1 ? games1 : games2;
    }

    public int getSets(int player) {
        return player == 1 ? sets1 : sets2;
    }

    public String getPoints(int player) {
        Integer points = player == 1 ? points1 : points2;
        if (isTieBreak) return null;
        if (points != null) {
            return switch (points) {
                case 1 -> "15";
                case 2 -> "30";
                case 3 -> "40";
                case 4 -> "AD";
                default -> "0";
            };
        }
        return null;
    }

    public Integer getTieBreakPoints(int player) {
        return player == 1 ? tieBreakPoints1 : tieBreakPoints2;
    }

    public PlayerModel getPlayer(int i){
        return i==1?playerModel1:playerModel2;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        MatchModel that = (MatchModel) o;
        return Objects.equals(playerModel1, that.playerModel1) && Objects.equals(playerModel2, that.playerModel2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerModel1, playerModel2);
    }
}
