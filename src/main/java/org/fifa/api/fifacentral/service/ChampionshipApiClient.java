package org.fifa.api.fifacentral.service;

import org.fifa.api.fifacentral.entity.Championship;
import org.fifa.api.fifacentral.entity.ChampionshipData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

@Service
public class ChampionshipApiClient {
    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(ChampionshipApiClient.class);

    @Value("${api.premier_league.url}")
    private String premierLeagueUrl;

    @Value("${api.la_liga.url}")
    private String laLigaUrl;

    public ChampionshipApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ChampionshipData fetchChampionshipData(Championship championship, String season) {
        try {
            // Supprime la partie de l'API key dans l'URL
            String url = getChampionshipUrl(championship) + "/data?season=" + season;
            logger.info("Fetching data from: {}", url);

            ResponseEntity<ChampionshipData> response = restTemplate.getForEntity(url, ChampionshipData.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("API returned status: " + response.getStatusCode());
            }

            if (response.getBody() == null) {
                throw new RuntimeException("Empty response body from API");
            }

            // Validation des données reçues
            ChampionshipData data = response.getBody();
            if ((data.getPlayers() == null || data.getPlayers().isEmpty()) &&
                    (data.getClubs() == null || data.getClubs().isEmpty())) {
                throw new RuntimeException("Invalid data received: no players or clubs found");
            }

            return data;
        } catch (Exception e) {
            logger.error("Error fetching data for {} - season {}: {}", championship, season, e.getMessage());
            throw new RuntimeException("Failed to fetch data for " + championship + " - season " + season, e);
        }
    }

    private String getChampionshipUrl(Championship championship) {
        // Si tu n'as pas besoin de clé API, on renvoie l'URL directement
        switch (championship) {
            case PREMIER_LEAGUE:
                return premierLeagueUrl;
            case LA_LIGA:
                return laLigaUrl;
            default:
                throw new IllegalArgumentException("Unknown championship: " + championship);
        }
    }
}
