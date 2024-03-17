package com.app.cinema.Seat;

import com.app.cinema.Exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeatService {
    private final ISeatDAO seatDao;

    public SeatService(ISeatDAO seatDao) {
        this.seatDao = seatDao;
    }

    public List<Seat> getSeats() {
        return seatDao.selectSeats();
    }

    public void addNewSeat(Seat seat) {
        Seat existingSeat = seatDao.selectSeatByCode(seat.seatCode());
        if (existingSeat != null) {
            throw new IllegalStateException("Seat already exists");
        }

        int result = seatDao.insertSeat(seat);
        if (result != 1) {
            throw new IllegalStateException("oops something went wrong");
        }
    }

    public void deleteSeat(Integer id) {
        Optional<Seat> seats = seatDao.selectSeatById(id);
        seats.ifPresentOrElse(seat -> {
            int result = seatDao.deleteSeat(id);
            if (result != 1) {
                throw new IllegalStateException("oops could not delete seat");
            }
        }, () -> {
            throw new NotFoundException(String.format("Seat with id %s not found", id));
        });
    }

    public Seat getSeat(int id) {
        return seatDao.selectSeatById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Seat with id %s not found", id)));
    }
}
