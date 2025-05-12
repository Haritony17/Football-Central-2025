package org.fifa.api.fifacentral.service;

import lombok.RequiredArgsConstructor;
import org.fifa.api.fifacentral.dao.CentralClubDAO;
import org.fifa.api.fifacentral.dao.CentralPlayerDAO;
import org.fifa.api.fifacentral.entity.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SyncService {
    private static final Logger logger = LoggerFactory.getLogger(SyncService.class);

    private final RestTemplate restTemplate;
    private final CentralClubDAO clubRepository;
    private final CentralPlayerDAO playerRepository;

    // Méthode sans besoin d'API Key
    public void syncAllChampionships(String season) {
        for (Championship championship : Championship.values()) {
            try {
                syncChampionship(championship, season);  // On passe simplement "season" sans l'API key
            } catch (Exception e) {
                logger.error("Failed to sync {}: {}", championship, e.getMessage());
            }
        }
    }

    private void syncChampionship(Championship championship, String season) {
        String url = getChampionshipUrl(championship) + "/api/seasons/" + season + "/stats";

        // Pas de clé API, donc les headers sont maintenant inutiles
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<ChampionshipData> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                ChampionshipData.class
        );

        if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
            throw new RuntimeException("Failed to fetch data for " + championship);
        }

        ChampionshipData data = response.getBody();
        LocalDateTime now = LocalDateTime.now();

        if (data.getClubs() != null) {
            List<CentralClub> clubs = data.getClubs().stream()
                    .peek(club -> {
                        club.setChampionship(championship);
                        club.setLastSync(now);
                    })
                    .toList();
            clubRepository.saveAll(clubs);
        }

        if (data.getPlayers() != null) {
            List<CentralPlayer> players = data.getPlayers().stream()
                    .peek(player -> {
                        player.setChampionship(championship);
                        player.setLastSync(now);
                    })
                    .toList();
            playerRepository.saveAll(players);
        }
    }

    private String getChampionshipUrl(Championship championship) {
        return switch (championship) {
            case PREMIER_LEAGUE -> "http://localhost:8082";
            case LA_LIGA -> "http://localhost:8083";
            default -> throw new IllegalArgumentException("Unknown championship: " + championship);
        };
    }
}
