package com.app.cinema.Showing;

import com.app.cinema.Exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShowingService {
    private final IShowingDAO showingDao;

    public ShowingService(IShowingDAO showingDao) {
        this.showingDao = showingDao;
    }

    public List<Showing> getShowings() {
        return showingDao.selectShowings();
    }

    public void addNewShowing(Showing showing) {
        int result = showingDao.insertShowing(showing);
        if (result != 1) {
            throw new IllegalStateException("oops something went wrong");
        }
    }

    public void deleteShowing(Integer id) {
        Optional<Showing> showings = showingDao.selectShowingById(id);
        showings.ifPresentOrElse(showing -> {
            int result = showingDao.deleteShowing(id);
            if (result != 1) {
                throw new IllegalStateException("oops could not delete showing");
            }
        }, () -> {
            throw new NotFoundException(String.format("Showing with id %s not found", id));
        });
    }

    public Showing getShowing(int id) {
        return showingDao.selectShowingById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Showing with id %s not found", id)));
    }
}
