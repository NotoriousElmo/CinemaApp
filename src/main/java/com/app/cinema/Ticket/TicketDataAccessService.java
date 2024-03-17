package com.app.cinema.Ticket;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class TicketDataAccessService implements ITicketDAO {

    private final JdbcTemplate jdbcTemplate;

    public TicketDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    @Override
    public List<Ticket> selectTickets() {
        String sql = """
                SELECT *
                FROM ticket
                LIMIT 100;
                """;

        return jdbcTemplate.query(sql, new TicketRowMapper());
    }

    @Override
    public int insertTicket(Ticket ticket) {
        String sql = """
            INSERT INTO ticket(price, seat, showing)
            VALUES (?, ?, ?);
            """;
        return jdbcTemplate.update(sql,
                ticket.price(),
                ticket.seat(),
                ticket.showing());
    }

    @Override
    public int deleteTicket(int id) {
        String sql = """
                DELETE
                FROM ticket
                WHERE id = ?;
                """;
        return jdbcTemplate
                .update(sql, id);
    }

    @Override
    public Optional<Ticket> selectTicketById(int id) {
        String sql = """
                SELECT *
                FROM ticket
                WHERE id = ?;
                """;
        return jdbcTemplate
                .query(sql, new TicketRowMapper(), id)
                .stream()
                .findFirst();
    }
}
