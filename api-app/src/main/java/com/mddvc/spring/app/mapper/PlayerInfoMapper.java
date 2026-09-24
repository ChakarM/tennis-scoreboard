package com.mddvc.spring.app.mapper;

import com.mddvc.spring.app.dto.PlayerInfoDto;
import com.mddvc.spring.domain.model.MatchModel;
import org.springframework.stereotype.Component;

@Component

public class PlayerInfoMapper {
    public PlayerInfoDto toDto(int i, MatchModel matchModel) {
        String name = matchModel.getPlayer(i).getName();
        String points = matchModel.getPoints(i);
        Integer games = matchModel.getGames(i);
        int sets = matchModel.getSets(i);
        Integer tieBreakPoints = matchModel.getTieBreakPoints(i);
     return new PlayerInfoDto(name,
                points,
                games,
                sets,
                tieBreakPoints);
    }
}
