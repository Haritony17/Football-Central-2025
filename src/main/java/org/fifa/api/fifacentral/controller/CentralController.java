package org.fifa.api.fifacentral.controller;

import lombok.RequiredArgsConstructor;
import org.fifa.api.fifacentral.service.ApiKeyService;
import org.fifa.api.fifacentral.service.SyncService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CentralController {
    private final SyncService syncService;
    private final ApiKeyService apiKeyService;

    @PostMapping("/synchronization")
    public ResponseEntity<String> synchronizeData(
            @RequestParam String season,
            @RequestHeader("X-API-KEY") String apiKey) {

        if (!apiKeyService.isValid(apiKey)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid API key");
        }

        syncService.syncAllChampionships(season, apiKey);
        return ResponseEntity.ok("Synchronization completed for season " + season);
    }

    @GetMapping("/generateApiKey")
    public ResponseEntity<String> generateApiKey() {
        return ResponseEntity.ok(apiKeyService.generateKey());
    }
}