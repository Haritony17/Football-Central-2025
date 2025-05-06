package org.fifa.api.fifacentral.service;

import lombok.RequiredArgsConstructor;
import org.fifa.api.fifacentral.dao.CentralClubDAO;
import org.fifa.api.fifacentral.entity.CentralClub;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubService {
    private final CentralClubDAO clubRepository;

    public List<CentralClub> getBestClubs(Integer top) {
        List<CentralClub> clubs = clubRepository.findAll();

        Comparator<CentralClub> comparator = Comparator
                .comparingInt(CentralClub::getRankingPoints).reversed()
                .thenComparingInt(c -> c.getScoredGoals() - c.getConcededGoals()).reversed()
                .thenComparingInt(CentralClub::getCleanSheetNumber).reversed();

        return clubs.stream()
                .sorted(comparator)
                .limit(top == null ? 5 : top)
                .peek(club -> club.setRank(clubs.indexOf(club) + 1))
                .collect(Collectors.toList());
    }
}
