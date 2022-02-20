CREATE SCHEMA IF NOT EXISTS cinema;

CREATE TABLE IF NOT EXISTS cinema.user (
    id SERIAL PRIMARY KEY,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    phone_number TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL
);