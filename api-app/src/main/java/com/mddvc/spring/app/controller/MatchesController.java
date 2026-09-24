package com.mddvc.spring.app.controller;

import com.mddvc.spring.app.dto.*;
import com.mddvc.spring.app.exception.MatchNotFoundException;
import com.mddvc.spring.app.mapper.FinishedMatchMapper;
import com.mddvc.spring.app.mapper.UnfinishedMatchMapper;
import com.mddvc.spring.app.service.MatchGameplayService;
import com.mddvc.spring.domain.model.MatchModel;
import com.mddvc.spring.domain.model.PlayerModel;
import com.mddvc.spring.domain.repository.ActiveMatchRegistry;
import com.mddvc.spring.domain.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/")
public class MatchesController {
    private final MatchGameplayService matchGameplayService;
    private final ActiveMatchRegistry activeMatchRegistry;
    private final MatchRepository matchRepository;
    private final UnfinishedMatchMapper unfinishedMatchMapper;
    private final FinishedMatchMapper finishedMatchMapper;

    @Autowired
    public MatchesController(MatchGameplayService matchGameplayService,
                             ActiveMatchRegistry activeMatchRegistry,
                             MatchRepository matchRepository,
                             UnfinishedMatchMapper unfinishedMatchMapper,
                             FinishedMatchMapper finishedMatchMapper) {
        this.matchGameplayService = matchGameplayService;
        this.activeMatchRegistry = activeMatchRegistry;
        this.matchRepository = matchRepository;
        this.unfinishedMatchMapper = unfinishedMatchMapper;
        this.finishedMatchMapper = finishedMatchMapper;
    }

    @PostMapping("/matches")
    @ResponseStatus(HttpStatus.CREATED)
    public MatchIdResponce createMatch(@RequestBody NewMatchDto newMatchDto) {
        PlayerModel player1 = new PlayerModel(newMatchDto.firstPlayerName());
        PlayerModel player2 = new PlayerModel(newMatchDto.secondPlayerName());
        MatchModel matchModel = new MatchModel(player1, player2);
        return new MatchIdResponce(activeMatchRegistry.create(matchModel));
    }

    @PostMapping("/matches/{uuid}/point")
    @ResponseStatus(HttpStatus.OK)
    public MatchDto scorePoint(@PathVariable("uuid") UUID uuid, @RequestBody ScorerDto scorer) {
        PlayerModel player = new PlayerModel(scorer.name());
        return matchGameplayService.scorePoint(uuid, player);
    }

    @GetMapping("/matches/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public MatchDto getScore(@PathVariable("uuid") UUID uuid) {
        Optional<MatchModel> foundMatch = activeMatchRegistry.findById(uuid);
        if (foundMatch.isEmpty()) {
            throw new MatchNotFoundException(uuid);
        }
        return unfinishedMatchMapper.toDto(foundMatch.get());
    }
    @GetMapping("/matches")
    @ResponseStatus(HttpStatus.OK)
    public FinishedMatchesDto getAllFinishedMatches(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                                    @RequestParam(value = "player_name", required = false, defaultValue = "") String playerName){

        List<FinishedMatchDto> result = matchRepository.findAll(playerName, page).stream().map(finishedMatchMapper::toDto).toList();
        long totalPages = matchRepository.getTotalPages(playerName);
        return new FinishedMatchesDto(result, page, totalPages);
    }
}

//14:50-15:03
//15:17-15:55
//16:13-16:50