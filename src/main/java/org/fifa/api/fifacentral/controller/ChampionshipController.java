package org.fifa.api.fifacentral.controller;

import lombok.RequiredArgsConstructor;
import org.fifa.api.fifacentral.service.ChampionshipService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ChampionshipController {
    private final ChampionshipService championshipService;

    @GetMapping("/championshipRankings")
    public ResponseEntity<List<ChampionshipService.ChampionshipRanking>> getChampionshipRankings() {
        return ResponseEntity.ok(championshipService.getChampionshipRankings());
    }
}