CREATE SCHEMA IF NOT EXISTS users;

CREATE TABLE users.users (
    id             UUID PRIMARY KEY,
    nickname       VARCHAR(64) NOT NULL,
    email          VARCHAR(255) NOT NULL,
    fist_name      VARCHAR(64),
    last_name      VARCHAR(64),
    date_of_birth  DATE,
    avatar         VARCHAR(255),
    description    VARCHAR(1000),
    country_id     INTEGER,
    city_id        INTEGER,
    rank_id        INTEGER,
    exp            INTEGER NOT NULL DEFAULT 0,
    language       VARCHAR(2),
    created_at     TIMESTAMPTZ NOT NULL,
    updated_at     TIMESTAMPTZ NOT NULL,

    CONSTRAINT uq_users_nickname UNIQUE (nickname),
    CONSTRAINT uq_users_email UNIQUE (email)
);