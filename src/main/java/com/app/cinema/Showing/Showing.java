package com.app.cinema.Showing;

import com.app.cinema.Ticket.Ticket;

import java.sql.Timestamp;
import java.util.List;

public record Showing(
        Integer id,
        Timestamp start,
        List<Ticket> tickets,
        Integer movie,
        Integer cinema
) {
}
