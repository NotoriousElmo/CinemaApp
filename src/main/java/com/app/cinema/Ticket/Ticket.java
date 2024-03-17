package com.app.cinema.Ticket;

public record Ticket(
        Integer id,
        Float price,
        boolean taken,
        Integer seat,
        Integer showing
) {
}
