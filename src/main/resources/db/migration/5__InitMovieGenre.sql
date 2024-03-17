CREATE TABLE movie_genre (
   id BIGSERIAL PRIMARY KEY,
   movie BIGINT REFERENCES movie (id),
   genre BIGINT REFERENCES genre (id),
   unique(movie, genre)
);