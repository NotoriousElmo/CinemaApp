CREATE TABLE cinema (
   id BIGSERIAL PRIMARY KEY,
   name TEXT NOT NULL,
   city TEXT NOT NULL,
   street TEXT NOT NULL,
   building TEXT NOT NULL,
   unique (name, building)
);