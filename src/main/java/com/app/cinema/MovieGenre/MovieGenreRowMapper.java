package com.app.cinema.MovieGenre;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieGenreRowMapper implements RowMapper<MovieGenre> {
    @Override
    public MovieGenre mapRow(ResultSet resultSet, int i) throws SQLException {
        return new MovieGenre(
                resultSet.getInt("id"),
                resultSet.getInt("movie"),
                resultSet.getInt("genre")
        );
    }
}