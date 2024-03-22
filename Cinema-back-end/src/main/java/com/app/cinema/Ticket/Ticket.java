package com.app.cinema.Ticket;

import java.sql.Timestamp;

public record Ticket(
        Integer id,
        Float price,
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
