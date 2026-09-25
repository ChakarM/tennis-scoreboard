package com.mddvc.spring.domain.model;

import java.util.Optional;

public class RegularGame implements GameState {
    private Point player1Score = Point.LOVE;
    private Point player2Score = Point.LOVE;

    @Override
    public boolean isFinished() {
        return getWinner().isPresent();
    }

    @Override
    public void scorePoint(int player) {
        if (isFinished()) return;

        //40-40
        if (player1Score == Point.FORTY && player2Score == Point.FORTY) {
            if (player == 1) player1Score = Point.ADVANTAGE;
            else player2Score = Point.ADVANTAGE;
            return;
        }

        //AD-40
        if (player1Score == Point.ADVANTAGE && player == 2) {
            player1Score = Point.FORTY;
            return;
        }

        //40-AD
        if (player2Score == Point.ADVANTAGE && player == 1) {
            player2Score = Point.FORTY;
            return;
        }

        //pre game
        if (player == 1) {
            if (player1Score == Point.ADVANTAGE || (player1Score == Point.FORTY && player2Score != Point.FORTY)) {
                player1Score = Point.GAME;
                return;
            }
        }

        if (player == 2) {
            if (player2Score == Point.ADVANTAGE || (player2Score == Point.FORTY && player1Score != Point.FORTY)) {
                player2Score = Point.GAME;
                return;
            }
        }

        //regular
        if (player == 1) player1Score = player1Score.getNextScore();
        else player2Score = player2Score.getNextScore();

    }

    @Override
    public Optional<Integer> getWinner() {
        if (player1Score == Point.GAME) return Optional.of(1);
        if (player2Score == Point.GAME) return Optional.of(2);
        return Optional.empty();
    }
}
