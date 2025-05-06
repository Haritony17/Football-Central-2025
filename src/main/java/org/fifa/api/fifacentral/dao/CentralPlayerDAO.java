package org.fifa.api.fifacentral.dao;

import lombok.RequiredArgsConstructor;
import org.fifa.api.fifacentral.entity.CentralPlayer;
import org.fifa.api.fifacentral.entity.Championship;
import org.fifa.api.fifacentral.entity.DurationUnit;
import org.fifa.api.fifacentral.entity.PlayerPosition;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CentralPlayerDAO {
    private final JdbcTemplate jdbcTemplate;

    public List<CentralPlayer> findAll() {
        String sql = "SELECT * FROM central_player";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new CentralPlayer(
                        rs.getString("id"),
                        rs.getString("name"),
                        rs.getInt("number"),
                        PlayerPosition.valueOf(rs.getString("position")),
                        rs.getString("nationality"),
                        rs.getInt("age"),
                        Championship.valueOf(rs.getString("championship")),
                        rs.getInt("scored_goals"),
                        rs.getDouble("playing_time_value"),
                        DurationUnit.valueOf(rs.getString("playing_time_unit")),
                        rs.getTimestamp("last_sync").toLocalDateTime(),
                        rs.getInt("rank")
                )
        );
    }

    public void saveAll(List<CentralPlayer> players) {
        String sql = """
            INSERT INTO central_player (
                id, name, number, position, nationality, age, championship, 
                scored_goals, playing_time_value, playing_time_unit, last_sync
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            ON CONFLICT (id) DO UPDATE SET
                name = EXCLUDED.name,
                number = EXCLUDED.number,
                position = EXCLUDED.position,
                nationality = EXCLUDED.nationality,
                age = EXCLUDED.age,
                championship = EXCLUDED.championship,
                scored_goals = EXCLUDED.scored_goals,
                playing_time_value = EXCLUDED.playing_time_value,
                playing_time_unit = EXCLUDED.playing_time_unit,
                last_sync = EXCLUDED.last_sync
            """;

        jdbcTemplate.batchUpdate(sql, players, players.size(),
                (ps, player) -> {
                    ps.setString(1, player.getId());
                    ps.setString(2, player.getName());
                    ps.setInt(3, player.getNumber());
                    ps.setString(4, player.getPosition().name());
                    ps.setString(5, player.getNationality());
                    ps.setInt(6, player.getAge());
                    ps.setString(7, player.getChampionship().name());
                    ps.setInt(8, player.getScoredGoals());
                    ps.setDouble(9, player.getPlayingTimeValue());
                    ps.setString(10, player.getPlayingTimeUnit().name());
                    ps.setTimestamp(11, Timestamp.valueOf(player.getLastSync()));
                }
        );
    }
}
