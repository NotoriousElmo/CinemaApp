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
            SELECT t.id, t.price,
                   s.id as seatId, s.code, s.room,
                   sh.id as showingId, sh.start,
                   m.name, m.age, m.language, m.length_minutes
            FROM ticket t
            LEFT JOIN seat s ON t.seat = s.id
            LEFT JOIN showing sh ON t.showing = sh.id
            LEFT JOIN movie m ON sh.movie = m.id
            ORDER BY sh.start ASC
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
                ticket.seatId(),
                ticket.showingId());
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
