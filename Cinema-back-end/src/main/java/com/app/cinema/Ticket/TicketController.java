package com.app.cinema.Ticket;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://10.102.0.218:8081")
@RestController
@RequestMapping("api/tickets")
public class TicketController {
    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping
    public List<Ticket> listTickets() {
        return ticketService.getTickets();
    }

    @GetMapping("{id}")
    public Ticket getTicketId(@PathVariable("id") Integer id) {
        return ticketService.getTicket(id);
    }

    @PostMapping
    public void addTicket(@RequestBody Ticket ticket) {
        System.out.println(ticket.movie());
        ticketService.addNewTicket(ticket);
    }

    @DeleteMapping("{id}")
    public void deleteTicket(@PathVariable("id") Integer id) {
        ticketService.deleteTicket(id);
    }
}
