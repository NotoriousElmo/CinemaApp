package com.app.cinema.MovieGenre;

import com.app.cinema.Exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieGenreService {

    private final IMovieGenreDAO movieGenreDAO;

    public MovieGenreService(IMovieGenreDAO movieGenreDAO) {
        this.movieGenreDAO = movieGenreDAO;
    }

    public List<MovieGenre> getMovieGenres() {
        return movieGenreDAO.selectMovieGenres();
    }

    public void addNewMovieGenre(MovieGenre movieGenre) {
        int result = movieGenreDAO.insertMovieGenre(movieGenre);
        if (result != 1) {
            throw new IllegalStateException("oops something went wrong");
        }
    }

    public void deleteMovieGenre(Integer id) {
        Optional<MovieGenre> movieGenres = movieGenreDAO.selectMovieGenreById(id);
        movieGenres.ifPresentOrElse(movieGenre -> {
            int result = movieGenreDAO.deleteMovieGenre(id);
            if (result != 1) {
                throw new IllegalStateException("oops could not delete movieGenre");
            }
        }, () -> {
            throw new NotFoundException(String.format("MovieGenre with id %s not found", id));
        });
    }

    public MovieGenre getMovieGenre(int id) {
        return movieGenreDAO.selectMovieGenreById(id)
                .orElseThrow(() -> new NotFoundException(String.format("MovieGenre with id %s not found", id)));
    }
}
