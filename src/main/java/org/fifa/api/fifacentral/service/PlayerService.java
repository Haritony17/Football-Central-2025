package org.fifa.api.fifacentral.service;


import lombok.RequiredArgsConstructor;
import org.fifa.api.fifacentral.dao.CentralPlayerDAO;
import org.fifa.api.fifacentral.entity.CentralPlayer;
import org.fifa.api.fifacentral.entity.DurationUnit;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerService {
    private final CentralPlayerDAO playerRepository;

    public List<CentralPlayer> getBestPlayers(Integer top, DurationUnit playingTimeUnit) {
        List<CentralPlayer> allPlayers = playerRepository.findAll();

        List<CentralPlayer> sortedPlayers = new ArrayList<>(allPlayers);

        sortedPlayers.sort(Comparator
                .comparingInt(CentralPlayer::getScoredGoals).reversed()
                .thenComparingDouble(p -> convertPlayingTimeToHours(p, playingTimeUnit))
        );
        return sortedPlayers.stream()
                .limit(top == null ? 5 : top)
                .peek(player -> player.setRank(sortedPlayers.indexOf(player) + 1))
                .collect(Collectors.toList());
    }

    private double convertPlayingTimeToHours(CentralPlayer player, DurationUnit targetUnit) {
        double valueInHours = player.getPlayingTimeUnit() == DurationUnit.HOUR ? player.getPlayingTimeValue() :
                player.getPlayingTimeUnit() == DurationUnit.MINUTE ? player.getPlayingTimeValue() / 60 :
                        player.getPlayingTimeValue() / 3600;

        return targetUnit == DurationUnit.HOUR ? valueInHours :
                targetUnit == DurationUnit.MINUTE ? valueInHours * 60 :
                        valueInHours * 3600;
    }
}