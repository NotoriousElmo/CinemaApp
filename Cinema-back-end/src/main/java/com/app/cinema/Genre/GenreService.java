package com.app.cinema.Genre;

import com.app.cinema.Exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GenreService {
    private final IGenreDAO genreDao;

    public GenreService(IGenreDAO movieDao) {
        this.genreDao = movieDao;
    }

    public List<Genre> getGenres() {
        return genreDao.selectGenres();
    }

    public void addNewGenre(Genre genre) {
        Genre existingGenre = genreDao.selectGenreByName(genre.name());
        if (existingGenre != null) {
            throw new IllegalStateException("Genre already exists");
        }

        int result = genreDao.insertGenre(genre);
        if (result != 1) {
            throw new IllegalStateException("oops something went wrong");
        }
    }

    public void deleteGenre(Integer id) {
        Optional<Genre> genres = genreDao.selectGenreById(id);
        genres.ifPresentOrElse(movie -> {
            int result = genreDao.deleteGenre(id);
            if (result != 1) {
                throw new IllegalStateException("oops could not delete genre");
            }
        }, () -> {
            throw new NotFoundException(String.format("Genre with id %s not found", id));
        });
    }

    public Genre getGenre(int id) {
        return genreDao.selectGenreById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Genre with id %s not found", id)));
    }
}
