package com.mddvc.spring.infrastructure.repository;

import com.mddvc.spring.domain.model.MatchModel;
import com.mddvc.spring.domain.repository.ActiveMatchRegistry;
import org.springframework.stereotype.Component;


import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryActiveRegistry implements ActiveMatchRegistry {

    private final Map<UUID, MatchModel> matches = new ConcurrentHashMap<>();


    @Override
    public UUID create(MatchModel matchModel) {
        UUID uuid = UUID.randomUUID();
        matches.put(uuid, matchModel);
        return uuid;
    }

    @Override
    public void remove(UUID uuid) {
        matches.remove(uuid);
    }

    @Override
    public Optional<MatchModel> findById(UUID uuid) {
        return Optional.ofNullable(matches.get(uuid));
    }
}
