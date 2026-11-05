CREATE SCHEMA IF NOT EXISTS post;

CREATE TABLE post.post (
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id      UUID        NOT NULL,
    city_id      INTEGER,
    country_id   INTEGER,
    content      TEXT,
    shared_count INTEGER     NOT NULL DEFAULT 0,
    exp          INTEGER     NOT NULL DEFAULT 20,
    created_at   TIMESTAMPTZ NOT NULL,
    updated_at   TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_post_user_id_created_at ON post.post (user_id, created_at DESC);
CREATE INDEX idx_post_city_id ON post.post (city_id);
CREATE INDEX idx_post_country_id ON post.post (country_id);

CREATE TABLE post.post_images (
    post_id  UUID    NOT NULL,
    position INTEGER NOT NULL,
    img_url  TEXT    NOT NULL,

    CONSTRAINT pk_post_images PRIMARY KEY (post_id, position),
    CONSTRAINT fk_post_images_post
        FOREIGN KEY (post_id) REFERENCES post.post (id) ON DELETE CASCADE
);
