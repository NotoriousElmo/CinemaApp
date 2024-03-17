package com.app.cinema.Genre;

import com.app.cinema.MovieGenre.MovieGenre;

import java.util.List;

public record Genre(
        Integer id,
        String name,
        List<MovieGenre> movieGenres
) {
}
