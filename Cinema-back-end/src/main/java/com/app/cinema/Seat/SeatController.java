package com.app.cinema.Seat;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://10.102.0.218:8081")
@RestController
@RequestMapping("api/seats")
public class SeatController {
    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping
    public List<Seat> listSeats() {
        return seatService.getSeats();
    }

    @GetMapping("{id}")
    public Seat getSeatId(@PathVariable("id") Integer id) {
        return seatService.getSeat(id);
    }

    @PostMapping
    public void addSeat(@RequestBody Seat seat) {
        seatService.addNewSeat(seat);
    }

    @DeleteMapping("{id}")
    public void deleteSeat(@PathVariable("id") Integer id) {
        seatService.deleteSeat(id);
    }
}
