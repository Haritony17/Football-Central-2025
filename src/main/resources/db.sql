
CREATE DATABASE fifa_central;

\c fifa_central


CREATE TYPE championship_name AS ENUM (
    'PREMIER_LEAGUE',
    'LA_LIGA',
    'BUNDESLIGA',
    'SERIA',
    'LIGUE_1'
);

CREATE TYPE player_position AS ENUM (
    'STRIKER',
    'MIDFIELDER',
    'DEFENSE',
    'GOAL_KEEPER'
);

CREATE TYPE duration_unit AS ENUM (
    'SECOND',
    'MINUTE',
    'HOUR'
);

CREATE TABLE championship (
                              name championship_name PRIMARY KEY,
                              difference_goals_median DECIMAL(10,2) NOT NULL DEFAULT 0,
                              last_sync TIMESTAMP WITH TIME ZONE
);


CREATE TABLE central_club (
                              id VARCHAR(36) PRIMARY KEY,
                              name VARCHAR(100) NOT NULL,
                              acronym VARCHAR(3) NOT NULL,
                              year_creation INT NOT NULL,
                              stadium VARCHAR(100) NOT NULL,
                              coach_name VARCHAR(100) NOT NULL,
                              coach_nationality VARCHAR(50) NOT NULL,
                              championship championship_name NOT NULL,
                              ranking_points INT NOT NULL DEFAULT 0,
                              scored_goals INT NOT NULL DEFAULT 0,
                              conceded_goals INT NOT NULL DEFAULT 0,
                              clean_sheet_number INT NOT NULL DEFAULT 0,
                              last_sync TIMESTAMP WITH TIME ZONE NOT NULL,
                              FOREIGN KEY (championship) REFERENCES championship(name)
                              rank INTEGER
);

CREATE TABLE central_player (
                                id VARCHAR(36) PRIMARY KEY,
                                name VARCHAR(100) NOT NULL,
                                number INT NOT NULL,
                                position player_position NOT NULL,
                                nationality VARCHAR(50) NOT NULL,
                                age INT NOT NULL,
                                championship championship_name NOT NULL,
                                scored_goals INT NOT NULL DEFAULT 0,
                                playing_time_value DECIMAL(10,2) NOT NULL DEFAULT 0,
                                playing_time_unit duration_unit NOT NULL DEFAULT 'MINUTE',
                                last_sync TIMESTAMP WITH TIME ZONE NOT NULL,
                                FOREIGN KEY (championship) REFERENCES championship(name)
                                rank INTEGER
);


