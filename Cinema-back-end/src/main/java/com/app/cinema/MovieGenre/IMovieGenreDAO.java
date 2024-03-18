package com.app.cinema.MovieGenre;

import java.util.List;
import java.util.Optional;

public interface IMovieGenreDAO {
    List<MovieGenre> selectMovieGenres();
    int insertMovieGenre(MovieGenre movieGenre);
    int deleteMovieGenre(int id);
    Optional<MovieGenre> selectMovieGenreById(int id);

}
