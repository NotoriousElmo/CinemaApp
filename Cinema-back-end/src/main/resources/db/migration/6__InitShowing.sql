CREATE TABLE showing (
   id BIGSERIAL PRIMARY KEY,
   start TIMESTAMP NOT NULL,
   price DECIMAL NOT NULL,
   room TEXT NOT NULL,
   movie BIGINT REFERENCES movie (id),
   cinema BIGINT REFERENCES cinema (id)
);