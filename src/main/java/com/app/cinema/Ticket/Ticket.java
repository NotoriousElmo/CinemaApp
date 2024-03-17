package com.app.cinema.Ticket;

public record Ticket(
        Integer id,
        Float price,
        Integer seat,
        Integer showing
) {
}
