package com.app.cinema.Movie;

import com.app.cinema.Genre.Genre;

import java.util.List;
import java.util.Optional;

public interface IMovieDAO {
    List<Movie> selectMovies();
    int insertMovie(Movie movie);
    int deleteMovie(int id);

    List<Genre> selectGenreByMovieName(String name);

    Optional<Movie> selectMovieById(int id);

    Movie selectMovieByName(String name);
}
