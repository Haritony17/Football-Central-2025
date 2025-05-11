package org.fifa.api.fifacentral.controller;

import lombok.RequiredArgsConstructor;
import org.fifa.api.fifacentral.entity.CentralPlayer;
import org.fifa.api.fifacentral.entity.DurationUnit;
import org.fifa.api.fifacentral.service.PlayerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PlayerController {
    private final PlayerService playerService;

    @GetMapping("/bestPlayers")
    public ResponseEntity<List<CentralPlayer>> getBestPlayers(
            @RequestParam(required = false, defaultValue = "5") Integer top,
            @RequestParam(required = false, defaultValue = "HOUR") DurationUnit playingTimeUnit) {

        if (top <= 0) {
            top = 5;
        }

        return ResponseEntity.ok(playerService.getBestPlayers(top, playingTimeUnit));
    }
}