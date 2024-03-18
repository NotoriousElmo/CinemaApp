package com.app.cinema.Movie;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://10.102.0.218:8081")
@RestController
@RequestMapping(path = "api/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<Movie> listMovies() {
        return movieService.getMovies();
    }

    @GetMapping("{id}")
    public Movie getMovieId(@PathVariable("id") Integer id) {
        return movieService.getMovie(id);
    }

    @PostMapping
    public void addMovie(@RequestBody Movie movie) {
        movieService.addNewMovie(movie);
    }

    @DeleteMapping("{id}")
    public void deleteMovie(@PathVariable("id") Integer id) {
        movieService.deleteMovie(id);
    }

}