package org.fifa.api.fifacentral.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CentralClub {
    private String id;
    private String name;
    private String acronym;
    private int yearCreation;
    private String stadium;
    private String coachName;
    private String coachNationality;
    private Championship championship;
    private int rankingPoints;
    private int scoredGoals;
    private int concededGoals;
    private int cleanSheetNumber;
    private LocalDateTime lastSync;
    private int rank;
}