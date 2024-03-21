package com.app.cinema.Showing;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ShowingDataAccessService implements IShowingDAO {

    private final JdbcTemplate jdbcTemplate;

    public ShowingDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<Showing> selectShowings() {
        String sql = """
                SELECT sh.id, sh.start,
                 m.id as movieId, m.name as movie, m.age,
                  c.id as cinemaId, c.name as cinema,
                   sh.room, m.length_minutes,
                  m.language, sh.price,
                  array_agg(g.name) as genres
                FROM showing sh
                LEFT JOIN movie m on sh.movie = m.id
                LEFT JOIN cinema c on sh.cinema = c.id
                LEFT JOIN movie_genre mg ON mg.movie = m.id
                LEFT JOIN genre g on mg.genre = g.id
                WHERE sh.start > now()
                GROUP BY sh.id, m.id, c.id
                ORDER BY sh.start ASC
                LIMIT 100;
                """;

        return jdbcTemplate.query(sql, new ShowingRowMapper());
    }

    @Override
    public int insertShowing(Showing showing) {
        String sql = """
            INSERT INTO showing(start)
            VALUES (?);
            """;
        return jdbcTemplate.update(sql,
                showing.start());
    }

    @Override
    public int deleteShowing(int id) {
        String sql = """
                DELETE
                FROM showing
                WHERE id = ?;
                """;
        return jdbcTemplate
                .update(sql, id);
    }

    @Override
    public Optional<Showing> selectShowingById(int id) {
        String sql = """
                SELECT *
                FROM showing
                WHERE id = ?;
                """;
        return jdbcTemplate
                .query(sql, new ShowingRowMapper(), id)
                .stream()
                .findFirst();
    }
}
