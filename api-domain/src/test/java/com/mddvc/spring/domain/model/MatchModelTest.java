package com.mddvc.spring.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatchModelTest {
    private MatchModel match;
    private PlayerModel p1;
    private PlayerModel p2;

    @BeforeEach
    void setUp() {
        p1 = new PlayerModel("Player One");
        p2 = new PlayerModel("Player Two");
        match = new MatchModel(p1, p2);
    }

    @Test
    void testDeuceAndAdvantage(){
        for (int i = 0; i < 3; i++) {
            match.pointWonBy(1);
            match.pointWonBy(2);
        }
        assertEquals("40", match.getPoints(1));
        assertEquals("40", match.getPoints(2));

        match.pointWonBy(1);
        assertEquals("AD", match.getPoints(1));

        match.pointWonBy(2);
        assertEquals("40", match.getPoints(1));
        assertEquals("40", match.getPoints(2));
    }

    @Test
    void testGameIsOngoingAfterPointWonWhenFortyForty() {
        for (int i = 0; i < 3; i++) {
            match.pointWonBy(1);
            match.pointWonBy(2);
        }
        match.pointWonBy(1);
        assertNull(match.getWinner());
    }


    @Test
    void testPlayerWinsAfterPointWonWhenFortyZero() {
        for (int i = 0; i < 3; i++) {
            match.pointWonBy(1);
        }
        match.pointWonBy(1);
        assertEquals(1, match.getGames(1));
    }

    void winGame(int player) {
        for (int i = 0; i < 4; i++) {
            match.pointWonBy(player);
        }
    }
    @Test
    void testTieBreakStartsAfterSixSix(){
        for (int i = 0; i < 6; i++) {
            winGame(1);
            winGame(2);
        }
        assertNotNull(match.getTieBreakPoints(1));
    }

    /*
    Если игрок 1 выигрывает очко при счёте 40-40, гейм не заканчивается
    Если игрок 1 выигрывает очко при счёте 40-0, то он выигрывает и гейм
    При счёте 6-6 начинается тайбрейк вместо обычного гейма
     */
}