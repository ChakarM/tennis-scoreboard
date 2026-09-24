package com.mddvc.spring.app.service;

import com.mddvc.spring.app.dto.MatchDto;
import com.mddvc.spring.app.exception.MatchNotFoundException;
import com.mddvc.spring.app.exception.NoSuchPlayerException;
import com.mddvc.spring.app.mapper.FinishedMatchMapper;
import com.mddvc.spring.app.mapper.UnfinishedMatchMapper;
import com.mddvc.spring.domain.model.MatchModel;
import com.mddvc.spring.domain.model.PlayerModel;
import com.mddvc.spring.domain.repository.ActiveMatchRegistry;
import com.mddvc.spring.domain.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class MatchGameplayService {
    private final ActiveMatchRegistry activeMatchRegistry;
    private final MatchRepository matchRepository;
    private final FinishedMatchMapper finishedMatchMapper;
    private final UnfinishedMatchMapper unfinishedMatchMapper;

    @Autowired
    public MatchGameplayService(ActiveMatchRegistry activeMatchRegistry,
                                MatchRepository matchRepository,
                                FinishedMatchMapper finishedMatchMapper,
                                UnfinishedMatchMapper unfinishedMatchMapper) {
        this.activeMatchRegistry = activeMatchRegistry;
        this.matchRepository = matchRepository;
        this.finishedMatchMapper = finishedMatchMapper;
        this.unfinishedMatchMapper = unfinishedMatchMapper;
    }

//    public String startMatch(PlayerModel player1, PlayerModel player2) {
//        MatchModel match = new MatchModel(player1, player2);
//        return activeMatchRegistry.create(match).toString();
//    }

    public MatchDto scorePoint(UUID uuid, PlayerModel scorer) {
        Optional<MatchModel> matchModel = activeMatchRegistry.findById(uuid);
        if (matchModel.isEmpty()) {
            throw new MatchNotFoundException(uuid);
        }
        MatchModel match = matchModel.get();
        int player;
        if (scorer.equals(match.getPlayer(1))) player = 1;
        else if (scorer.equals(match.getPlayer(2))) {
            player = 2;
        }
        else throw new NoSuchPlayerException(String.format("Player %s is not playing in this match", scorer.getName()));

        match.pointWonBy(player);

        if (match.getWinner() != null) {
            matchRepository.save(match);
            activeMatchRegistry.remove(uuid);
            return finishedMatchMapper.toDto(match);
        }

        return unfinishedMatchMapper.toDto(match);

    }
}
