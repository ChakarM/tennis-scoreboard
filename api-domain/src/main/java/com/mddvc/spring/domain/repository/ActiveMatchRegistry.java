package com.mddvc.spring.domain.repository;

import com.mddvc.spring.domain.model.MatchModel;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


public interface ActiveMatchRegistry {
    UUID create(MatchModel matchModel);
    void remove(UUID uuid);
    Optional<MatchModel> findById(UUID uuid);

}
