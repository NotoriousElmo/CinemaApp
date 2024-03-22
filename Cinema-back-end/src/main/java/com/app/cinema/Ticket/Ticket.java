package com.app.cinema.Ticket;

import java.math.BigDecimal;
import java.sql.Timestamp;

public record Ticket(
        Integer id,
        BigDecimal price,
        Integer seatId,
        String seat,
        String room,
        Integer showingId,
        Timestamp showing,
        String movie,
        String age,
        String language,
        Integer length_minutes
) {
}
