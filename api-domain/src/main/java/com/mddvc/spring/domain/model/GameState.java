package com.mddvc.spring.domain.model;

import java.util.Optional;

public interface GameState {
    boolean isFinished();
    void scorePoint(int player);
    Optional<Integer> getWinner();
}
