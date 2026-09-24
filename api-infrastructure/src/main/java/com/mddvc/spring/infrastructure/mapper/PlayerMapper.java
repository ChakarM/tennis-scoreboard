package com.mddvc.spring.infrastructure.mapper;

import com.mddvc.spring.domain.model.PlayerModel;
import com.mddvc.spring.infrastructure.entity.PlayerEntity;

public class PlayerMapper {
    public PlayerModel toModel(PlayerEntity playerEntity) {
        return new PlayerModel(playerEntity.getName(), playerEntity.getId());
    }

    public PlayerEntity toEntity(PlayerModel playerModel) {
        return new PlayerEntity(playerModel.getName());
    }
}
