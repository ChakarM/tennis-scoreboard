package com.mddvc.spring.domain.model;

import java.util.Optional;

public class SetModel {
    private int games1 = 0;
    private int games2 = 0;

    private GameState currentGame = new RegularGame();

    public void scorePoint(int player) {
        if (isFinished()) return;
        currentGame.scorePoint(player);
        if (currentGame.isFinished()){
            Integer gameWinner = currentGame.getWinner().get();
            if (gameWinner == 1) games1++;
            else games2++;

            if (!isFinished()) {
                if (games1 == 6 && games2 == 6) {
                    currentGame = new TieBreakGame(); //тай-брейк
                } else {
                    currentGame = new RegularGame();  //обычный гейм
                }
            }
        }
    }

    public Optional<Integer> getWinner(){
        if (games1 == 7 && games2 == 6) return Optional.of(1); // Победил Игрок 1
        if (games2 == 7 && games1 == 6) return Optional.of(2);
        if (games1 >= 6 && games1 - games2 >= 2) return Optional.of(1);
        if (games2 >= 6 && games2 - games1 >= 2) return Optional.of(2);
        return Optional.empty();
    }

    public boolean isFinished(){
        return getWinner().isPresent();
    }

    public int getGame(int player) {
        return player == 1 ? games1 : games2;
    }

    public GameState getCurrentGameState(){
        return currentGame;
    }
}
