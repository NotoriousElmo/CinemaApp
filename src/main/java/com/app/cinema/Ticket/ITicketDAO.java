package com.app.cinema.Ticket;

import java.util.List;
import java.util.Optional;

public interface ITicketDAO {
    List<Ticket> selectTickets();
    int insertTicket(Ticket ticket);
    int deleteTicket(int id);
    Optional<Ticket> selectTicketById(int id);
}
