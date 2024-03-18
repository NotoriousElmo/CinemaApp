package com.app.cinema.Genre;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class GenreDataAccessService implements IGenreDAO {

    private final JdbcTemplate jdbcTemplate;

    public GenreDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<Genre> selectGenres() {
        String sql = """
                SELECT *
                FROM genre
                LIMIT 100;
                """;

        return jdbcTemplate.query(sql, new GenreRowMapper());
    }

    @Override
    public int insertGenre(Genre genre) {
        String sql = """
            INSERT INTO genre(name)
            VALUES (?);
            """;
        return jdbcTemplate.update(sql,
                genre.name());
    }

    @Override
    public int deleteGenre(int id) {
        String sql = """
                DELETE
                FROM genre
                WHERE id = ?;
                """;
        return jdbcTemplate
                .update(sql, id);
    }

    @Override
    public Optional<Genre> selectGenreById(int id) {
        String sql = """
                SELECT *
                FROM genre
                WHERE id = ?;
                """;
        return jdbcTemplate
                .query(sql, new GenreRowMapper(), id)
                .stream()
                .findFirst();
    }

    @Override
    public Genre selectGenreByName(String name) {
        String sql = """
                SELECT *
                FROM genre
                WHERE name LIKE ?;
                """;

        String searchName = "%" + name + "%";
        return jdbcTemplate
                .query(sql, new GenreRowMapper(), searchName)
                .stream()
                .findFirst()
                .orElse(null);
    }
}
