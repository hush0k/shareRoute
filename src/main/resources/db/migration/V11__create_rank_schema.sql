CREATE TABLE gamefication.rank (
    id    BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    exp   INTEGER,
    image TEXT
);

CREATE TABLE gamefication.rank_translation (
    id             BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    rank_id        BIGINT NOT NULL,
    language_code  TEXT,
    name           TEXT,
    description    TEXT,

    CONSTRAINT fk_rank_translation_rank
        FOREIGN KEY (rank_id) REFERENCES gamefication.rank (id),
    CONSTRAINT uq_rank_translation_rank_language
        UNIQUE (rank_id, language_code)
);