package org.fifa.api.fifacentral.service;


import lombok.RequiredArgsConstructor;
import org.fifa.api.fifacentral.dao.CentralClubDAO;
import org.fifa.api.fifacentral.dao.CentralPlayerDAO;
import org.fifa.api.fifacentral.entity.CentralClub;
import org.fifa.api.fifacentral.entity.CentralPlayer;
import org.fifa.api.fifacentral.entity.Championship;
import org.fifa.api.fifacentral.entity.ChampionshipData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SyncService {
    private final ChampionshipApiClient apiClient;
    private final CentralClubDAO clubRepository;
    private final CentralPlayerDAO playerRepository;


    public void syncAllChampionships(String season) {
        syncChampionship(Championship.PREMIER_LEAGUE, season);
        syncChampionship(Championship.LA_LIGA, season);
        syncChampionship(Championship.BUNDESLIGA, season);
        syncChampionship(Championship.SERIA, season);
        syncChampionship(Championship.LIGUE_1, season);
    }

    private void syncChampionship(Championship championship, String season) {
        ChampionshipData data = apiClient.fetchChampionshipData(championship, season);
        LocalDateTime now = LocalDateTime.now();

        if (data.getClubs() != null) {
            List<CentralClub> clubs = data.getClubs().stream()
                    .map(club -> {
                        club.setChampionship(championship);
                        club.setLastSync(now);
                        return club;
                    })
                    .toList();
            clubRepository.saveAll(clubs);
        }

        if (data.getPlayers() != null) {
            List<CentralPlayer> players = data.getPlayers().stream()
                    .map(player -> {
                        player.setChampionship(championship);
                        player.setLastSync(now);
                        return player;
                    })
                    .toList();
            playerRepository.saveAll(players);
        }
    }
}
