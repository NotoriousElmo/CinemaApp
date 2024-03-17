CREATE TABLE seat (
   id BIGSERIAL PRIMARY KEY,
   room TEXT NOT NULL,
   code TEXT NOT NULL,
   cinema BIGINT REFERENCES cinema (id),
   unique (code)
);