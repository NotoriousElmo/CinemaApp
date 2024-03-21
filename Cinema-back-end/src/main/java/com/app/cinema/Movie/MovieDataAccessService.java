package com.app.cinema.Movie;

import com.app.cinema.Genre.Genre;
import com.app.cinema.Genre.GenreRowMapper;
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
    public List<Genre> selectGenreByMovieName(String name) {
        String sql = """
                SELECT g.id, g.name
                FROM movie m
                LEFT JOIN movie_genre mg on m.id = mg.movie
                LEFT JOIN genre g on mg.genre = g.id
                WHERE m.name LIKE ?;
                """;
        return jdbcTemplate.query(sql, new GenreRowMapper(), "%" + name + "%");
    }

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
