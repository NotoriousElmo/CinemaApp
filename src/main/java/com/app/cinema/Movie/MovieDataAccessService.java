package com.app.cinema.Movie;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MovieDataAccessService implements IMovieDAO {

    private final JdbcTemplate jdbcTemplate;

    public MovieDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<Movie> selectMovies() {
        String sql = """
                SELECT *
                FROM movie 
                LIMIT 100;
                """;

        return jdbcTemplate.query(sql, new MovieRowMapper());
    }

    @Override
    public int insertMovie(Movie movie) {
        String sql = """
            INSERT INTO movie(name, age, language, length_minutes) 
            VALUES (?, ?, ?, ?);
            """;
        return jdbcTemplate.update(sql,
                movie.name(),
                movie.ageLimit(),
                movie.language(),
                movie.lengthInMinutes());
    }

    @Override
    public int deleteMovie(int id) {
        String sql = """
                DELETE 
                FROM movie
                WHERE id = ?;
                """;
        return jdbcTemplate
                .update(sql, id);
    }

    @Override
    public Optional<Movie> selectMovieById(int id) {
        String sql = """
                SELECT *
                FROM movie
                WHERE id = ?;
                """;
        return jdbcTemplate
                .query(sql, new MovieRowMapper(), id)
                .stream()
                .findFirst();
    }

    @Override
    public Movie selectMovieByName(String name) {
        String sql = """
                SELECT *
                FROM movie
                WHERE name LIKE ?;
                """;

        String searchName = "%" + name + "%";
        return jdbcTemplate
                .query(sql, new MovieRowMapper(), searchName)
                .stream()
                .findFirst()
                .orElse(null);
    }
}
