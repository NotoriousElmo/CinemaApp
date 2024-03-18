package com.app.cinema.Showing;

import java.util.List;
import java.util.Optional;

public interface IShowingDAO {
    List<Showing> selectShowings();
    int insertShowing(Showing showing);
    int deleteShowing(int id);
    Optional<Showing> selectShowingById(int id);
}
