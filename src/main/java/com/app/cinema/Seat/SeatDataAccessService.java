package com.app.cinema.Seat;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class SeatDataAccessService implements ISeatDAO {

    private final JdbcTemplate jdbcTemplate;

    public SeatDataAccessService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Seat> selectSeats() {
        String sql = """
                SELECT *
                FROM seat
                LIMIT 100;
                """;

        return jdbcTemplate.query(sql, new SeatRowMapper());
    }

    @Override
    public int insertSeat(Seat seat) {
        String sql = """
            INSERT INTO seat(room, code)
            VALUES (?, ?);
            """;
        return jdbcTemplate.update(sql,
                seat.room(),
                seat.seatCode());
    }

    @Override
    public int deleteSeat(int id) {
        String sql = """
                DELETE
                FROM seat
                WHERE id = ?;
                """;
        return jdbcTemplate
                .update(sql, id);
    }

    @Override
    public Optional<Seat> selectSeatById(int id) {
        String sql = """
                SELECT *
                FROM seat
                WHERE id = ?;
                """;
        return jdbcTemplate
                .query(sql, new SeatRowMapper(), id)
                .stream()
                .findFirst();
    }

    @Override
    public Seat selectSeatByCode(String code) {
        String sql = """
                SELECT *
                FROM seat
                WHERE code LIKE ?;
                """;

        String searchCode = "%" + code + "%";
        return jdbcTemplate
                .query(sql, new SeatRowMapper(), searchCode)
                .stream()
                .findFirst()
                .orElse(null);
    }
}
