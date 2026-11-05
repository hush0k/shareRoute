CREATE TABLE gamefication.achievement (
    id    UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    level INTEGER,
    exp   INTEGER,
    image TEXT
);

CREATE TABLE gamefication.achievement_condition (
    id               BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    achievement_id   UUID NOT NULL,
    condition_type   TEXT,
    number_of_action BIGINT,
    metadata         JSONB,

    CONSTRAINT fk_achievement_condition_achievement
        FOREIGN KEY (achievement_id) REFERENCES gamefication.achievement (id),
    CONSTRAINT uq_achievement_condition_achievement
        UNIQUE (achievement_id)
);

CREATE TABLE gamefication.achievement_translation (
    id             UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    achievement_id UUID NOT NULL,
    language_code  TEXT,
    name           TEXT,
    description    TEXT,

    CONSTRAINT fk_achievement_translation_achievement
        FOREIGN KEY (achievement_id) REFERENCES gamefication.achievement (id)
);

CREATE TABLE gamefication.user_achievement (
    id              UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id         UUID NOT NULL,
    achievement_id  UUID NOT NULL,
    completed_count BIGINT,
    is_achieved     BOOLEAN DEFAULT FALSE,
    achieved_at     TIMESTAMPTZ,

    CONSTRAINT fk_user_achievement_achievement
        FOREIGN KEY (achievement_id) REFERENCES gamefication.achievement (id),
    CONSTRAINT uq_user_achievement_user_achievement
        UNIQUE (user_id, achievement_id)
);
