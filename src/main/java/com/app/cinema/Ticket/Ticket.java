package com.app.cinema.Ticket;

import java.sql.Timestamp;

public record Ticket(
        Integer id,
        Float price,
        String seat,
        String room,
        Timestamp showing,
        String movie,
        String age,
        String language,
        Integer length_minutes
) {
}
