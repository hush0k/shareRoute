CREATE SCHEMA IF NOT EXISTS auth;

CREATE TYPE auth.role AS ENUM ('ADMIN', 'USER', 'MODERATOR');

CREATE TABLE auth.credentials (
    id                  UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id             UUID NOT NULL,
    email               VARCHAR(255) NOT NULL,
    hashed_password     VARCHAR(255) NOT NULL,
    role                VARCHAR(20) NOT NULL DEFAULT 'USER'
                        CHECK (role IN ('ADMIN', 'USER', 'MODERATOR')),
    is_email_verified   BOOLEAN NOT NULL DEFAULT FALSE,
    is_blocked          BOOLEAN NOT NULL DEFAULT FALSE,
    created_at          TIMESTAMPTZ NOT NULL,
    updated_at          TIMESTAMPTZ NOT NULL,

    CONSTRAINT uq_credentials_user_id UNIQUE (user_id),
    CONSTRAINT uq_credentials_email UNIQUE (email)
);

CREATE TABLE auth.refresh_token (
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id      UUID NOT NULL,
    token_hash   VARCHAR(255) NOT NULL,
    device_info  VARCHAR(255),
    expires_at   TIMESTAMPTZ NOT NULL,
    is_revoked   BOOLEAN NOT NULL DEFAULT FALSE,
    created_at   TIMESTAMPTZ NOT NULL,

    CONSTRAINT uq_refresh_token_token_hash UNIQUE (token_hash)
);

CREATE INDEX idx_refresh_token_user_id ON auth.refresh_token (user_id);

CREATE TABLE auth.login_attempt (
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id       UUID,
    ip_address    VARCHAR(64),
    is_successful BOOLEAN NOT NULL DEFAULT FALSE,
    created_at    TIMESTAMPTZ NOT NULL
);

CREATE INDEX idx_login_attempt_user_id ON auth.login_attempt (user_id);
