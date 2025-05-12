package org.fifa.api.fifacentral.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.fifa.api.fifacentral.dao.CentralClubDAO;
import org.fifa.api.fifacentral.entity.CentralClub;
import org.fifa.api.fifacentral.entity.Championship;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChampionshipService {
    private final CentralClubDAO clubRepository;

    public List<ChampionshipRanking> getChampionshipRankings() {
        List<ChampionshipRanking> rankings = Arrays.stream(Championship.values())
                .map(this::calculateRanking)
                .sorted(Comparator.comparingDouble(ChampionshipRanking::getDifferenceGoalsMedian))
                .collect(Collectors.toList());


        for (int i = 0; i < rankings.size(); i++) {
            rankings.get(i).setRank(i + 1);
        }

        return rankings;
    }

    private ChampionshipRanking calculateRanking(Championship championship) {
        List<CentralClub> clubs = clubRepository.findAllByChampionship(championship);

        if (clubs.isEmpty()) {
            return new ChampionshipRanking(0, championship, 0.0);
        }

        List<Integer> differences = clubs.stream()
                .map(club -> club.getScoredGoals() - club.getConcededGoals())
                .sorted()
                .toList();

        double median = calculateMedian(differences);
        return new ChampionshipRanking(0, championship, median);
    }

    private double calculateMedian(List<Integer> values) {
        if (values.isEmpty()) return 0;

        int size = values.size();
        if (size % 2 == 0) {
            return (values.get(size/2 - 1) + values.get(size/2)) / 2.0;
        } else {
            return values.get(size/2);
        }
    }

    @Data
    @AllArgsConstructor
    public static class ChampionshipRanking {
        private int rank;
        private Championship championship;
        private double differenceGoalsMedian;
    }
}