package org.fifa.api.fifacentral.dao;



import lombok.RequiredArgsConstructor;
import org.fifa.api.fifacentral.entity.CentralClub;
import org.fifa.api.fifacentral.entity.Championship;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CentralClubDAO {
    private final JdbcTemplate jdbcTemplate;

    public List<CentralClub> findAll() {
        String sql = "SELECT * FROM central_club";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new CentralClub(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getString("acronym"),
                        rs.getInt("year_creation"),
                        rs.getString("stadium"),
                        rs.getString("coach_name"),
                        rs.getString("coach_nationality"),
                        Championship.valueOf(rs.getString("championship")),
                        rs.getInt("ranking_points"),
                        rs.getInt("scored_goals"),
                        rs.getInt("conceded_goals"),
                        rs.getInt("clean_sheet_number"),
                        rs.getTimestamp("last_sync").toLocalDateTime(),
                        rs.getInt("rank")
                )
        );
    }

    public void saveAll(List<CentralClub> clubs) {
        String sql = """
            INSERT INTO central_club (
                id, name, acronym, year_creation, stadium, coach_name, 
                coach_nationality, championship, ranking_points, scored_goals, 
                conceded_goals, clean_sheet_number, last_sync
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            ON CONFLICT (id) DO UPDATE SET
                name = EXCLUDED.name,
                acronym = EXCLUDED.acronym,
                year_creation = EXCLUDED.year_creation,
                stadium = EXCLUDED.stadium,
                coach_name = EXCLUDED.coach_name,
                coach_nationality = EXCLUDED.coach_nationality,
                championship = EXCLUDED.championship,
                ranking_points = EXCLUDED.ranking_points,
                scored_goals = EXCLUDED.scored_goals,
                conceded_goals = EXCLUDED.conceded_goals,
                clean_sheet_number = EXCLUDED.clean_sheet_number,
                last_sync = EXCLUDED.last_sync
            """;

        jdbcTemplate.batchUpdate(sql, clubs, clubs.size(),
                (ps, club) -> {
                    ps.setString(1, club.getId());
                    ps.setString(2, club.getName());
                    ps.setString(3, club.getAcronym());
                    ps.setInt(4, club.getYearCreation());
                    ps.setString(5, club.getStadium());
                    ps.setString(6, club.getCoachName());
                    ps.setString(7, club.getCoachNationality());
                    ps.setString(8, club.getChampionship().name());
                    ps.setInt(9, club.getRankingPoints());
                    ps.setInt(10, club.getScoredGoals());
                    ps.setInt(11, club.getConcededGoals());
                    ps.setInt(12, club.getCleanSheetNumber());
                    ps.setTimestamp(13, Timestamp.valueOf(club.getLastSync()));
                }
        );
    }
    public List<CentralClub> findAllByChampionship(Championship championship) {
        String sql = "SELECT * FROM central_club WHERE championship = ?";
        return jdbcTemplate.query(sql, this::mapClub, championship.name());
    }

    private CentralClub mapClub(ResultSet rs, int rowNum) throws SQLException {
        return new CentralClub(
                rs.getString("id"),
                rs.getString("name"),
                rs.getString("acronym"),
                rs.getInt("year_creation"),
                rs.getString("stadium"),
                rs.getString("coach_name"),
                rs.getString("coach_nationality"),
                Championship.valueOf(rs.getString("championship")),
                rs.getInt("ranking_points"),
                rs.getInt("scored_goals"),  // Correction: scored_goals au lieu de getScoredGoals
                rs.getInt("conceded_goals"), // Correction: conceded_goals au lieu de getConcededGoals
                rs.getInt("clean_sheet_number"),
                rs.getTimestamp("last_sync").toLocalDateTime(),
                rs.getInt("rank")
        );
    }
}
