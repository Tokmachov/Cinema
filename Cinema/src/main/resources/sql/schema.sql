CREATE SCHEMA IF NOT EXISTS cinema;

CREATE TABLE IF NOT EXISTS cinema.user (
    id SERIAL PRIMARY KEY,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    phone_number TEXT UNIQUE NOT NULL,
    password TEXT NOT NULL
);

create table if not exists cinema.user_authentications (
    id serial primary key,
    date_and_time timestamptz,
    user_id int not null,
    address int,
    constraint fk_user foreign key (user_id) references cinema.user (id)
);