package com.app.cinema.Cinema;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CinemaRowMapper implements RowMapper<Cinema> {
    @Override
    public Cinema mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Cinema(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                resultSet.getString("city"),
                resultSet.getString("street"),
                resultSet.getString("building"),
                List.of()
        );
    }
}
