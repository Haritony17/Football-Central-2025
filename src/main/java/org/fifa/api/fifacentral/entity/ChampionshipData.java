package org.fifa.api.fifacentral.entity;

import lombok.Data;

import java.util.List;

@Data
public class ChampionshipData {
    private List<CentralPlayer> players;
    private List<CentralClub> clubs;
}
