package com.app.cinema.MovieGenre;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("api/movie_genres")
public class MovieGenreController {
    private final MovieGenreService movieGenreService;

    public MovieGenreController(MovieGenreService movieGenreService) {
        this.movieGenreService = movieGenreService;
    }

    @GetMapping
    public List<MovieGenre> listMovies() {
        return movieGenreService.getMovieGenres();
    }

    @GetMapping("{id}")
    public MovieGenre getMovieId(@PathVariable("id") Integer id) {
        return movieGenreService.getMovieGenre(id);
    }

    @PostMapping
    public void addMovie(@RequestBody MovieGenre movieGenre) {
        movieGenreService.addNewMovieGenre(movieGenre);
    }

    @DeleteMapping("{id}")
    public void deleteMovie(@PathVariable("id") Integer id) {
        movieGenreService.deleteMovieGenre(id);
    }
}
