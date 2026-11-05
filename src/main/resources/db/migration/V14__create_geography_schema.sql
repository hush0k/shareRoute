CREATE SCHEMA IF NOT EXISTS geography;

CREATE TABLE geography.country (
    id         INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    iso_code   VARCHAR(2) NOT NULL,
    phone_code TEXT,
    currency   VARCHAR(3),

    CONSTRAINT uq_country_iso_code UNIQUE (iso_code)
);

CREATE TABLE geography.country_translation (
    id            INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    country_id    INTEGER NOT NULL,
    language_code TEXT    NOT NULL,
    name          TEXT    NOT NULL,

    CONSTRAINT fk_country_translation_country
        FOREIGN KEY (country_id) REFERENCES geography.country (id) ON DELETE CASCADE,
    CONSTRAINT uq_country_translation_country_language
        UNIQUE (country_id, language_code)
);

CREATE TABLE geography.city (
    id            INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    country_id    INTEGER NOT NULL,
    original_name TEXT    NOT NULL,
    city_type     TEXT    NOT NULL,
    latitude      DOUBLE PRECISION,
    longitude     DOUBLE PRECISION,
    timezone      TEXT,

    CONSTRAINT fk_city_country
        FOREIGN KEY (country_id) REFERENCES geography.country (id)
);

CREATE INDEX idx_city_country_id ON geography.city (country_id);

CREATE TABLE geography.city_translation (
    id            INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    city_id       INTEGER NOT NULL,
    language_code TEXT    NOT NULL,
    name          TEXT    NOT NULL,

    CONSTRAINT fk_city_translation_city
        FOREIGN KEY (city_id) REFERENCES geography.city (id) ON DELETE CASCADE,
    CONSTRAINT uq_city_translation_city_language
        UNIQUE (city_id, language_code)
);
