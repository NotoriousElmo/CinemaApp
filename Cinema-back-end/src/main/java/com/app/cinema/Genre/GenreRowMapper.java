package com.app.cinema.Genre;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class GenreRowMapper implements RowMapper<Genre> {
    @Override
    public Genre mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Genre(
                resultSet.getInt("id"),
                resultSet.getString("name"),
                List.of()
        );
    }
}
