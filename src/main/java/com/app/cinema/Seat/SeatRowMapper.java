package com.app.cinema.Seat;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SeatRowMapper implements RowMapper<Seat> {
    @Override
    public Seat mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Seat(
                resultSet.getInt("id"),
                resultSet.getString("room"),
                resultSet.getString("code"),
                List.of(),
                resultSet.getInt("cinema")
        );
    }
}
