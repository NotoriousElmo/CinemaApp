package com.app.cinema.Cinema;

import com.app.cinema.Showing.Showing;

import java.util.List;

public record Cinema(Integer id,
                     String name,
                     String city,
                     String street,
                     String building,
                     List<Showing> showings) {
}
