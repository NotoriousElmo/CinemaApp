package com.app.cinema.Cinema;

import java.util.List;
import java.util.Optional;

public interface ICinemaDAO {
    List<Cinema> selectCinemas();
    int insertCinema(Cinema cinema);
    int deleteCinema(int id);
    Optional<Cinema> selectCinemaById(int id);

    Cinema selectCinemaByName(String name);
}
