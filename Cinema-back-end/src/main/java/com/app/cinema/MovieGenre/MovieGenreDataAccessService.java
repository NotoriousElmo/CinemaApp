package com.app.cinema.MovieGenre;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class MovieGenreDataAccessService implements IMovieGenreDAO {

    private final JdbcTemplate jdbcTemplate;

    public MovieGenreDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<MovieGenre> selectMovieGenres() {
        String sql = """
                SELECT *
                FROM movie_genre
                LIMIT 100;
                """;

        return jdbcTemplate.query(sql, new MovieGenreRowMapper());
    }

    @Override
    public int insertMovieGenre(MovieGenre movieGenre) {
        String sql = """
            INSERT INTO movie_genre (movie, genre) VALUES (?, ?);
            """;
        return jdbcTemplate.update(sql,
                movieGenre.movie(),
                movieGenre.genre());
    }

    @Override
    public int deleteMovieGenre(int id) {
        String sql = """
                DELETE
                FROM movie_genre
                WHERE id = ?;
                """;
        return jdbcTemplate
                .update(sql, id);
    }

    @Override
    public Optional<MovieGenre> selectMovieGenreById(int id) {
        String sql = """
                SELECT *
                FROM movie_genre
                WHERE id = ?;
                """;
        return jdbcTemplate
                .query(sql, new MovieGenreRowMapper(), id)
                .stream()
                .findFirst();
    }
}
