package com.app.cinema.Movie;

import java.util.List;
import java.util.Optional;

public interface IMovieDAO {
    List<Movie> selectMovies();
    int insertMovie(Movie movie);
    int deleteMovie(int id);
    Optional<Movie> selectMovieById(int id);

    Movie selectMovieByName(String name);
}
