package com.app.cinema.Seat;

import com.app.cinema.Ticket.Ticket;

import java.util.List;

public record Seat(
        Integer id,
        String room,
        String seatCode,
        List<Ticket> tickets,
        Integer cinema
) {
}
