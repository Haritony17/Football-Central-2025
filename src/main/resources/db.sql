-- Création de la base de données
CREATE DATABASE fifa_central;

-- Connexion à la base
\c fifa_central

-- Types ENUM
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

-- Table des championnats
CREATE TABLE championship (
                              name championship_name PRIMARY KEY,
                              difference_goals_median DECIMAL(10,2) NOT NULL DEFAULT 0,
                              last_sync TIMESTAMP WITH TIME ZONE
);

-- Table des clubs
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
);

-- Table des joueurs
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
);

-- Peuplement des données de test
INSERT INTO championship (name, difference_goals_median, last_sync) VALUES
                                                                        ('PREMIER_LEAGUE', 12.5, NOW()),
                                                                        ('LA_LIGA', 8.2, NOW()),
                                                                        ('BUNDESLIGA', 15.0, NOW()),
                                                                        ('SERIA', 9.7, NOW()),
                                                                        ('LIGUE_1', 10.3, NOW());

INSERT INTO central_club (
    id, name, acronym, year_creation, stadium,
    coach_name, coach_nationality, championship,
    ranking_points, scored_goals, conceded_goals, clean_sheet_number, last_sync
) VALUES
      ('club1', 'Paris Saint-Germain', 'PSG', 1970, 'Parc des Princes',
       'Christophe Galtier', 'French', 'LIGUE_1',
       85, 70, 30, 15, NOW()),

      ('club2', 'Real Madrid', 'RMA', 1902, 'Santiago Bernabeu',
       'Carlo Ancelotti', 'Italian', 'LA_LIGA',
       78, 65, 28, 12, NOW()),

      ('club3', 'Bayern Munich', 'FCB', 1900, 'Allianz Arena',
       'Julian Nagelsmann', 'German', 'BUNDESLIGA',
       82, 80, 25, 18, NOW());

INSERT INTO central_player (
    id, name, number, position, nationality, age,
    championship, scored_goals, playing_time_value, playing_time_unit, last_sync
) VALUES
      ('player1', 'Kylian Mbappé', 7, 'STRIKER', 'French', 24,
       'LIGUE_1', 28, 2500.0, 'MINUTE', NOW()),

      ('player2', 'Karim Benzema', 9, 'STRIKER', 'French', 35,
       'LA_LIGA', 22, 2300.0, 'MINUTE', NOW()),

      ('player3', 'Lionel Messi', 30, 'STRIKER', 'Argentinian', 36,
       'LIGUE_1', 18, 2100.0, 'MINUTE', NOW()),

      ('player4', 'Vinicius Junior', 20, 'STRIKER', 'Brazilian', 22,
       'LA_LIGA', 15, 2400.0, 'MINUTE', NOW()),

      ('player5', 'Joshua Kimmich', 6, 'MIDFIELDER', 'German', 28,
       'BUNDESLIGA', 8, 2700.0, 'MINUTE', NOW());



CREATE USER fifa_central_user WITH PASSWORD 'fifa123';
GRANT ALL PRIVILEGES ON DATABASE fifa_central TO fifa_central_user;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO fifa_central_user;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA public TO fifa_central_user;