package com.app.cinema.Genre;

import java.util.List;
import java.util.Optional;

public interface IGenreDAO {
    List<Genre> selectGenres();
    int insertGenre(Genre genre);
    int deleteGenre(int id);
    Optional<Genre> selectGenreById(int id);

    Genre selectGenreByName(String name);
}
