-- Enum tables
CREATE TYPE championship AS ENUM (
    'PREMIER_LEAGUE',
    'LA_LIGA'
);

CREATE TYPE duration_unit AS ENUM (
    'SECOND',
    'MINUTE',
    'HOUR'
);

CREATE TYPE player_position AS ENUM (
    'STRIKER',
    'MIDFIELDER',
    'DEFENSE',
    'GOAL_KEEPER'
);

-- Central Club table
CREATE TABLE central_club (
                              id VARCHAR(36) PRIMARY KEY,
                              name VARCHAR(100) NOT NULL,
                              acronym VARCHAR(10),
                              year_creation INT,
                              stadium VARCHAR(100),
                              coach_name VARCHAR(100),
                              coach_nationality VARCHAR(50),
                              championship championship NOT NULL,
                              ranking_points INT DEFAULT 0,
                              scored_goals INT DEFAULT 0,
                              conceded_goals INT DEFAULT 0,
                              clean_sheet_number INT DEFAULT 0,
                              last_sync TIMESTAMP NOT NULL,
                              rank INT
);

-- Central Player table
CREATE TABLE central_player (
                                id VARCHAR(36) PRIMARY KEY,
                                name VARCHAR(100) NOT NULL,
                                number INT,
                                position player_position,
                                nationality VARCHAR(50),
                                age INT,
                                championship championship NOT NULL,
                                scored_goals INT DEFAULT 0,
                                playing_time_value DOUBLE PRECISION,
                                playing_time_unit duration_unit,
                                last_sync TIMESTAMP NOT NULL,
                                rank INT
);






-- Insert sample clubs
INSERT INTO central_club (
    id, name, acronym, year_creation, stadium, coach_name,
    coach_nationality, championship, ranking_points, scored_goals,
    conceded_goals, clean_sheet_number, last_sync, rank
) VALUES
      ('club-1', 'Manchester United', 'MUFC', 1878, 'Old Trafford', 'Erik ten Hag',
       'Netherlands', 'PREMIER_LEAGUE', 75, 68, 43, 12, NOW(), 1),

      ('club-2', 'Real Madrid', 'RMA', 1902, 'Santiago Bernabeu', 'Carlo Ancelotti',
       'Italy', 'LA_LIGA', 85, 75, 35, 15, NOW(), 1),

      ('club-3', 'FC Barcelona', 'FCB', 1899, 'Camp Nou', 'Xavi Hernandez',
       'Spain', 'LA_LIGA', 80, 70, 30, 14, NOW(), 2),

      ('club-4', 'Liverpool FC', 'LFC', 1892, 'Anfield', 'Jurgen Klopp',
       'Germany', 'PREMIER_LEAGUE', 70, 65, 40, 10, NOW(), 2);

-- Insert sample players
INSERT INTO central_player (
    id, name, number, position, nationality, age,
    championship, scored_goals, playing_time_value, playing_time_unit, last_sync, rank
) VALUES
      ('player-1', 'Mohamed Salah', 11, 'STRIKER', 'Egypt', 30,
       'PREMIER_LEAGUE', 25, 30.5, 'HOUR', NOW(), 1),

      ('player-2', 'Kevin De Bruyne', 17, 'MIDFIELDER', 'Belgium', 31,
       'PREMIER_LEAGUE', 15, 28.2, 'HOUR', NOW(), 2),

      ('player-3', 'Karim Benzema', 9, 'STRIKER', 'France', 35,
       'LA_LIGA', 28, 25.8, 'HOUR', NOW(), 1),

      ('player-4', 'Vinicius Junior', 20, 'STRIKER', 'Brazil', 22,
       'LA_LIGA', 18, 27.3, 'HOUR', NOW(), 2),

      ('player-5', 'Virgil van Dijk', 4, 'DEFENSE', 'Netherlands', 31,
       'PREMIER_LEAGUE', 3, 29.1, 'HOUR', NOW(), 5),

      ('player-6', 'Robert Lewandowski', 9, 'STRIKER', 'Poland', 34,
       'LA_LIGA', 23, 26.7, 'HOUR', NOW(), 3);