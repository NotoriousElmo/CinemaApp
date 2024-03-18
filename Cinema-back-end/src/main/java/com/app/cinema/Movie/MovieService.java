package com.app.cinema.Movie;

import com.app.cinema.Exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieService {
    private final IMovieDAO movieDao;

    public MovieService(IMovieDAO movieDao) {
        this.movieDao = movieDao;
    }

    public List<Movie> getMovies() {
        return movieDao.selectMovies();
    }

    public void addNewMovie(Movie movie) {
        Movie existingMovie = movieDao.selectMovieByName(movie.name());
        if (existingMovie != null) {
            throw new IllegalStateException("Movie already exists");
        }

        int result = movieDao.insertMovie(movie);
        if (result != 1) {
            throw new IllegalStateException("oops something went wrong");
        }
    }

    public void deleteMovie(Integer id) {
        Optional<Movie> movies = movieDao.selectMovieById(id);
        movies.ifPresentOrElse(movie -> {
            int result = movieDao.deleteMovie(id);
            if (result != 1) {
                throw new IllegalStateException("oops could not delete movie");
            }
        }, () -> {
            throw new NotFoundException(String.format("Movie with id %s not found", id));
        });
    }

    public Movie getMovie(int id) {
        return movieDao.selectMovieById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Movie with id %s not found", id)));
    }
}
