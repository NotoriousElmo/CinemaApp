package com.app.cinema.Genre;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://10.102.0.218:8081")
@RestController
@RequestMapping(path = "api/genres")
public class GenreController {

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public List<Genre> listGenres() {
        return genreService.getGenres();
    }

    @GetMapping("{id}")
    public Genre getGenreId(@PathVariable("id") Integer id) {
        return genreService.getGenre(id);
    }

    @PostMapping
    public void addGenre(@RequestBody Genre genre) {
        genreService.addNewGenre(genre);
    }

    @DeleteMapping("{id}")
    public void deleteGenre(@PathVariable("id") Integer id) {
        genreService.deleteGenre(id);
    }

}