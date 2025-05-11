package org.fifa.api.fifacentral.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CentralPlayer {
    private String id;
    private String name;
    private int number;
    private PlayerPosition position;
    private String nationality;
    private int age;
    private Championship championship;
    private int scoredGoals;
    private double playingTimeValue;
    private DurationUnit playingTimeUnit;
    private LocalDateTime lastSync;
    private int rank;
}