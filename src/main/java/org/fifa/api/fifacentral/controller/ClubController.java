package org.fifa.api.fifacentral.controller;

import lombok.RequiredArgsConstructor;
import org.fifa.api.fifacentral.entity.CentralClub;
import org.fifa.api.fifacentral.service.ClubService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ClubController {
    private final ClubService clubService;

    @GetMapping("/bestClubs")
    public ResponseEntity<List<CentralClub>> getBestClubs(
            @RequestParam(required = false) Integer top) {

        return ResponseEntity.ok(clubService.getBestClubs(top));
    }
}
