package org.fifa.api.fifacentral.controller;

import lombok.RequiredArgsConstructor;
import org.fifa.api.fifacentral.service.SyncService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CentralController {
    private final SyncService syncService;

    @PostMapping("/synchronization")
    public ResponseEntity<String> synchronizeData(
            @RequestParam String season) {

        if (season == null || season.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Season parameter is required");
        }

        try {
            syncService.syncAllChampionships(season);
            return ResponseEntity.ok("Synchronization completed for season " + season);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to synchronize data: " + e.getMessage());
        }
    }

    // Génération d'une clé API, si besoin pour d'autres usages
    @GetMapping("/generateApiKey")
    public ResponseEntity<String> generateApiKey() {
        return ResponseEntity.ok("API Key generation is disabled in this configuration.");
    }
}
