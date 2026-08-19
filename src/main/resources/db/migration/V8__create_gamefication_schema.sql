CREATE SCHEMA IF NOT EXISTS gamefication;

CREATE TABLE gamefication.archetype (
    id          BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    image       TEXT,
    description TEXT
);

CREATE TABLE gamefication.archetype_translation (
    id            BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    archetype_id  BIGINT NOT NULL,
    name          TEXT,
    language_code TEXT,

    CONSTRAINT fk_archetype_translation_archetype
        FOREIGN KEY (archetype_id) REFERENCES gamefication.archetype (id),
    CONSTRAINT uq_archetype_translation_archetype_language
        UNIQUE (archetype_id, language_code)
);

CREATE TABLE gamefication.user_archetype (
    id             BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    archetype_id   BIGINT NOT NULL,
    user_id        UUID NOT NULL,
    is_owned       BOOLEAN,
    discovered_at  TIMESTAMPTZ,
    unlocked_at    TIMESTAMPTZ,

    CONSTRAINT fk_user_archetype_archetype
        FOREIGN KEY (archetype_id) REFERENCES gamefication.archetype (id),
    CONSTRAINT uq_user_archetype_user_archetype
        UNIQUE (user_id, archetype_id)
);