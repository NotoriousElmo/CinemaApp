package com.app.cinema.Ticket;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TicketRowMapper implements RowMapper<Ticket> {
    @Override
    public Ticket mapRow(ResultSet resultSet, int i) throws SQLException {
        return new Ticket(
                resultSet.getInt("id"),
                resultSet.getFloat("price"),
                resultSet.getInt("seat"),
                resultSet.getInt("showing")
        );
    }
}
