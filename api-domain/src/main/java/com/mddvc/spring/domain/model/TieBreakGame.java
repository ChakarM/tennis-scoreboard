package com.mddvc.spring.domain.model;

import java.util.Optional;

public class TieBreakGame implements GameState {
    private int points1 = 0;
    private int points2 = 0;

    @Override
    public boolean isFinished() {
        return getWinner().isPresent();
    }

    @Override
    public void scorePoint(int player) {
        if (isFinished()) return;
        if (player == 1) points1++;
        else points2++;
    }

    @Override
    public Optional<Integer> getWinner() {
        if (points1 >= 7 && points1 - points2 >= 2) return Optional.of(1);
        if (points2 >= 7 && points2 - points1 >= 2) return Optional.of(2);
        return Optional.empty();
    }
}
