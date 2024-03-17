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
                SELECT *
                FROM showing 
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
