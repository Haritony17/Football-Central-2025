package org.fifa.api.fifacentral.service;

import org.fifa.api.fifacentral.entity.Championship;
import org.fifa.api.fifacentral.entity.ChampionshipData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChampionshipApiClient {
    private final RestTemplate restTemplate;
    private static final Logger logger = LoggerFactory.getLogger(ChampionshipApiClient.class);

    @Value("${api.premier_league.url}")
    private String premierLeagueUrl;

    @Value("${api.la_liga.url}")
    private String laLigaUrl;

    @Value("${api.bundesliga.url}")
    private String bundesligaUrl;

    @Value("${api.seria.url}")
    private String seriaUrl;

    @Value("${api.ligue1.url}")
    private String ligue1Url;

    @Value("${api.key}")
    private String apiKey;

    public ChampionshipApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ChampionshipData fetchChampionshipData(Championship championship, String season) {
        try {
            String url = getChampionshipUrl(championship) + "/data?season=" + season + "&apiKey=" + apiKey;
            logger.info("Fetching data from: {}", url);

            ResponseEntity<ChampionshipData> response = restTemplate.getForEntity(url, ChampionshipData.class);

            if (!response.getStatusCode().is2xxSuccessful()) {
                throw new RuntimeException("API returned status: " + response.getStatusCode());
            }

            if (response.getBody() == null) {
                throw new RuntimeException("Empty response body from API");
            }

            return response.getBody();
        } catch (Exception e) {
            logger.error("Error fetching data for {} - season {}: {}", championship, season, e.getMessage());
            throw new RuntimeException("Failed to fetch data for " + championship + " - season " + season, e);
        }
    }

    private String getChampionshipUrl(Championship championship) {
        return switch (championship) {
            case PREMIER_LEAGUE -> premierLeagueUrl;
            case LA_LIGA -> laLigaUrl;
            case BUNDESLIGA -> bundesligaUrl;
            case SERIA -> seriaUrl;
            case LIGUE_1 -> ligue1Url;
        };
    }
}