package com.mddvc.spring.infrastructure.mapper;

import com.mddvc.spring.domain.model.MatchModel;
import com.mddvc.spring.domain.model.PlayerModel;
import com.mddvc.spring.infrastructure.entity.MatchEntity;
import com.mddvc.spring.infrastructure.entity.PlayerEntity;

public class MatchMapper {
    private static final PlayerMapper playerMapper = new PlayerMapper();

    public MatchEntity toEntity(MatchModel matchModel) {
        PlayerEntity player1 = playerMapper.toEntity(matchModel.getPlayer(1));
        PlayerEntity player2 = playerMapper.toEntity(matchModel.getPlayer(2));
        PlayerEntity winner = playerMapper.toEntity(matchModel.getWinner());
        return new MatchEntity(player1, player2, winner);
    }

    public MatchModel toModel(MatchEntity matchEntity) {
        PlayerModel player1 = playerMapper.toModel(matchEntity.getPlayer1());
        PlayerModel player2 = playerMapper.toModel(matchEntity.getPlayer2());
        PlayerModel winner = playerMapper.toModel(matchEntity.getWinner());
        return new MatchModel(player1, player2, winner);
    }
}
