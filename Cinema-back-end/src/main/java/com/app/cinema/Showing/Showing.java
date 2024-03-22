package com.app.cinema.Showing;

import java.math.BigDecimal;
import java.sql.Timestamp;

public record Showing(
        Integer id,
        Timestamp start,
        Integer movieId,
        String movie,
        String age,
        Integer cinemaId,
        String cinema,
        String room,
        Integer length_minutes,
        String language,
        BigDecimal price,
        String[] genres
) {
}
