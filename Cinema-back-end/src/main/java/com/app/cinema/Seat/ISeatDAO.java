package com.app.cinema.Seat;

import java.util.List;
import java.util.Optional;

public interface ISeatDAO {
    List<Seat> selectSeats();
    int insertSeat(Seat seat);
    int deleteSeat(int id);
    Optional<Seat> selectSeatById(int id);
    Seat selectSeatByCode(String code);
}
