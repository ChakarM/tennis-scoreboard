package com.mddvc.spring.domain.repository;

import com.mddvc.spring.domain.model.MatchModel;
import com.mddvc.spring.domain.model.PlayerModel;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface MatchRepository {
    void save(MatchModel matchModel);

    Long getTotalPages(String name);

    List<MatchModel> findAll(String name, int page);
}
