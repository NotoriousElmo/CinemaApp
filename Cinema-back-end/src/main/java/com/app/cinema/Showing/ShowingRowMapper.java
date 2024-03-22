package com.app.cinema.Showing;

import org.springframework.jdbc.core.RowMapper;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class ShowingRowMapper implements RowMapper<Showing> {
    @Override
    public Showing mapRow(ResultSet resultSet, int i) throws SQLException {
        String[] genres = (String[]) resultSet.getArray("genres").getArray();
        return new Showing(
                resultSet.getInt("id"),
                Timestamp.valueOf(resultSet.getString("start")),
                resultSet.getInt("movieId"),
                resultSet.getString("movie"),
                resultSet.getString("age"),
                resultSet.getInt("cinemaId"),
                resultSet.getString("cinema"),
                resultSet.getString("room"),
                resultSet.getInt("length_minutes"),
                resultSet.getString("language"),
                resultSet.getFloat("price"),
                genres
        );
    }
}