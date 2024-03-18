CREATE TABLE movie (
   id BIGSERIAL PRIMARY KEY,
   name TEXT NOT NULL,
   age TEXT NOT NULL,
   language TEXT NOT NULL,
   length_minutes INTEGER NOT NULL,
   unique (name)
);