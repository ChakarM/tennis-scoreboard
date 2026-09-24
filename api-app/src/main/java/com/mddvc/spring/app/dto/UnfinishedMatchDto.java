package com.mddvc.spring.app.dto;

import lombok.Getter;

@Getter
public class UnfinishedMatchDto extends MatchDto {
    private final PlayerInfoDto firstPlayer;
    private final PlayerInfoDto secondPlayer;
    private final String winnerName;

    public UnfinishedMatchDto(PlayerInfoDto firstPlayer, PlayerInfoDto secondPlayer, String winnerName) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.winnerName = winnerName;
    }
}
