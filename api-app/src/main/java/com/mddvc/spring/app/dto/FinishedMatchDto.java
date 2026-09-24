package com.mddvc.spring.app.dto;

import lombok.Getter;

@Getter
public class FinishedMatchDto extends MatchDto {
    private final String firstPlayerName;
    private final String secondPlayerName;
    private final String winnerName;

    public FinishedMatchDto(String firstPlayerName, String secondPlayerName, String winnerName) {
        this.firstPlayerName = firstPlayerName;
        this.secondPlayerName = secondPlayerName;
        this.winnerName = winnerName;
    }
}
