package com.mddvc.spring.app.mapper;

import com.mddvc.spring.app.dto.FinishedMatchDto;
import com.mddvc.spring.domain.model.MatchModel;
import org.springframework.stereotype.Component;

@Component
public class FinishedMatchMapper{
    public FinishedMatchDto toDto(MatchModel matchModel){
        String firstPlayerName = matchModel.getPlayer(1).getName();
        String secondPlayerName = matchModel.getPlayer(2).getName();
        String winnerName = matchModel.getWinner().getName();
        return new FinishedMatchDto(firstPlayerName,
                secondPlayerName,
                winnerName);
    }
}
