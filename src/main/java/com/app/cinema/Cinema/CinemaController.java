package com.app.cinema.Cinema;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/cinemas")
public class CinemaController {
    private final CinemaService cinemaService;

    public CinemaController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @GetMapping
    public List<Cinema> listCinemas() {
        return cinemaService.getCinemas();
    }

    @GetMapping("{id}")
    public Cinema getCinemaId(@PathVariable("id") Integer id) {
        return cinemaService.getCinema(id);
    }

    @PostMapping
    public void addCinema(@RequestBody Cinema cinema) {
        cinemaService.addNewCinema(cinema);
    }

    @DeleteMapping("{id}")
    public void deleteCinema(@PathVariable("id") Integer id) {
        cinemaService.deleteCinema(id);
    }
}
