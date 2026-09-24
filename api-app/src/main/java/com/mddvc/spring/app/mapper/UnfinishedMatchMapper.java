package com.mddvc.spring.app.mapper;

import com.mddvc.spring.app.dto.PlayerInfoDto;
import com.mddvc.spring.app.dto.UnfinishedMatchDto;
import com.mddvc.spring.domain.model.MatchModel;
import org.springframework.stereotype.Component;

@Component

public class UnfinishedMatchMapper{
    private final PlayerInfoMapper playerInfoMapper = new PlayerInfoMapper();
    public UnfinishedMatchDto toDto(MatchModel matchModel) {
        PlayerInfoDto player1 = playerInfoMapper.toDto(1, matchModel);
        PlayerInfoDto player2 = playerInfoMapper.toDto(2, matchModel);
        String winner = (matchModel.getWinner() != null) ? matchModel.getWinner().getName() : null;

        return new UnfinishedMatchDto(
                player1,
                player2,
                winner
        );
    }
}
