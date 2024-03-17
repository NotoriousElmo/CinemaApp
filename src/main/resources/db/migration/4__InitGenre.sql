CREATE TABLE genre (
   id BIGSERIAL PRIMARY KEY,
   name TEXT NOT NULL,
   unique (name)
);