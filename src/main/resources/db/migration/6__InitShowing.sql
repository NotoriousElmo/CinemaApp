CREATE TABLE showing (
   id BIGSERIAL PRIMARY KEY,
   start TIMESTAMP NOT NULL,
   movie BIGINT REFERENCES movie (id),
   cinema BIGINT REFERENCES cinema (id)
);