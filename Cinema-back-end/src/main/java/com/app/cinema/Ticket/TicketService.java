package com.app.cinema.Ticket;

import com.app.cinema.Exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TicketService {
    private final ITicketDAO ticketDao;

    public TicketService(ITicketDAO ticketDao) {
        this.ticketDao = ticketDao;
    }

    public List<Ticket> getTickets() {
        return ticketDao.selectTickets();
    }

    public void addNewTicket(Ticket ticket) {

        int result = ticketDao.insertTicket(ticket);
        if (result != 1) {
            throw new IllegalStateException("oops something went wrong");
        }
    }

    public void deleteTicket(Integer id) {
        Optional<Ticket> tickets = ticketDao.selectTicketById(id);
        tickets.ifPresentOrElse(movie -> {
            int result = ticketDao.deleteTicket(id);
            if (result != 1) {
                throw new IllegalStateException("oops could not delete ticket");
            }
        }, () -> {
            throw new NotFoundException(String.format("Ticket with id %s not found", id));
        });
    }

    public Ticket getTicket(int id) {
        return ticketDao.selectTicketById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Ticket with id %s not found", id)));
    }
}
