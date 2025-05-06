package org.fifa.api.fifacentral.controller;


import lombok.RequiredArgsConstructor;
import org.fifa.api.fifacentral.service.SyncService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CentralController {
    private final SyncService syncService;

    @PostMapping("/synchronization")
    public ResponseEntity<String> synchronizeData(@RequestParam String season) {
        syncService.syncAllChampionships(season);
        return ResponseEntity.ok("Synchronization completed for season " + season);
    }
}
