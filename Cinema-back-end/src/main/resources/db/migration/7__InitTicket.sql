CREATE TABLE ticket (
   id BIGSERIAL PRIMARY KEY,
   seat BIGINT REFERENCES seat (id),
   showing BIGINT REFERENCES showing (id),
   price FLOAT NOT NULL,
   unique (seat, showing)
);