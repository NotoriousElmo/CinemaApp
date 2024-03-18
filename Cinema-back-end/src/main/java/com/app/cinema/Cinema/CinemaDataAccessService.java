package com.app.cinema.Cinema;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CinemaDataAccessService implements ICinemaDAO {

    private final JdbcTemplate jdbcTemplate;

    public CinemaDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<Cinema> selectCinemas() {
        String sql = """
                SELECT *
                FROM cinema
                LIMIT 100;
                """;

        return jdbcTemplate.query(sql, new CinemaRowMapper());
    }

    @Override
    public int insertCinema(Cinema cinema) {
        String sql = """
            INSERT INTO cinema(name, city, street, building)
            VALUES (?, ?, ?, ?);
            """;
        return jdbcTemplate.update(sql,
                cinema.name(),
                cinema.city(),
                cinema.street(),
                cinema.building());
    }

    @Override
    public int deleteCinema(int id) {
        String sql = """
                DELETE
                FROM cinema
                WHERE id = ?;
                """;
        return jdbcTemplate
                .update(sql, id);
    }

    @Override
    public Optional<Cinema> selectCinemaById(int id) {
        String sql = """
                SELECT *
                FROM cinema
                WHERE id = ?;
                """;
        return jdbcTemplate
                .query(sql, new CinemaRowMapper(), id)
                .stream()
                .findFirst();
    }

    @Override
    public Cinema selectCinemaByName(String name) {
        String sql = """
                SELECT *
                FROM cinema
                WHERE name LIKE ?;
                """;

        String searchName = "%" + name + "%";
        return jdbcTemplate
                .query(sql, new CinemaRowMapper(), searchName)
                .stream()
                .findFirst()
                .orElse(null);
    }
}
