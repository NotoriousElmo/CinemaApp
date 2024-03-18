package com.app.cinema.Movie;

import com.app.cinema.MovieGenre.MovieGenre;
import com.app.cinema.Showing.Showing;

import java.util.List;

public record Movie(
        Integer id,
        String name,
        String ageLimit,
        String language,
        Integer lengthInMinutes,
        List<Showing> showings,
        List<MovieGenre> movieGenres
) {
}
