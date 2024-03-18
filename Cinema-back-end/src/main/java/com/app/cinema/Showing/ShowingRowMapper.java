package com.app.cinema.Showing;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

public class ShowingRowMapper implements RowMapper<Showing> {
    @Override
    public Showing mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Showing(
                resultSet.getInt("id"),
                Timestamp.valueOf(resultSet.getString("start")),
                List.of(),
                resultSet.getInt("cinema"),
                resultSet.getInt("movie")
        );
    }
}