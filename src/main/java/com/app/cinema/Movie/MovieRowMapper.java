package com.app.cinema.Movie;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class MovieRowMapper implements RowMapper<Movie> {
    @Override
    public Movie mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Movie(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("age"),
                resultSet.getString("language"),
                resultSet.getInt("length_minutes"),
                List.of(),
                List.of()
        );
    }
}
