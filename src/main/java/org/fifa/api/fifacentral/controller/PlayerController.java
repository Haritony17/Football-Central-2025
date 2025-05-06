package org.fifa.api.fifacentral.controller;

import lombok.RequiredArgsConstructor;
import org.fifa.api.fifacentral.entity.CentralPlayer;
import org.fifa.api.fifacentral.entity.DurationUnit;
import org.fifa.api.fifacentral.service.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;

    @GetMapping("/bestPlayers")
    public ResponseEntity<List<CentralPlayer>> getBestPlayers(
            @RequestParam(required = false) Integer top,
            @RequestParam(required = true) DurationUnit playingTimeUnit) {

        return ResponseEntity.ok(playerService.getBestPlayers(top, playingTimeUnit));
    }
}