package com.app.cinema.Cinema;

import com.app.cinema.Exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CinemaService {
    private final ICinemaDAO cinemaDao;

    public CinemaService(ICinemaDAO cinemaDao) {
        this.cinemaDao = cinemaDao;
    }

    public List<Cinema> getCinemas() {
        return cinemaDao.selectCinemas();
    }

    public void addNewCinema(Cinema cinema) {
        Cinema existingCinema = cinemaDao.selectCinemaByName(cinema.name());
        if (existingCinema != null) {
            throw new IllegalStateException("Cinema already exists");
        }

        int result = cinemaDao.insertCinema(cinema);
        if (result != 1) {
            throw new IllegalStateException("oops something went wrong");
        }
    }

    public void deleteCinema(Integer id) {
        Optional<Cinema> cinemas = cinemaDao.selectCinemaById(id);
        cinemas.ifPresentOrElse(cinema -> {
            int result = cinemaDao.deleteCinema(id);
            if (result != 1) {
                throw new IllegalStateException("oops could not delete cinema");
            }
        }, () -> {
            throw new NotFoundException(String.format("Cinema with id %s not found", id));
        });
    }

    public Cinema getCinema(int id) {
        return cinemaDao.selectCinemaById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Cinema with id %s not found", id)));
    }
}
