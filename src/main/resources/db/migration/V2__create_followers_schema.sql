CREATE SCHEMA IF NOT EXISTS followers;

CREATE TABLE followers.followers (
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    follower_id  UUID NOT NULL,
    following_id UUID NOT NULL,
    created_at   TIMESTAMPTZ NOT NULL,

    CONSTRAINT uq_followers_follower_following UNIQUE (follower_id, following_id)
);