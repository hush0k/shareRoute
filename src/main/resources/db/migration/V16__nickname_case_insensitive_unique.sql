ALTER TABLE users.users DROP CONSTRAINT uq_users_nickname;

CREATE UNIQUE INDEX uq_users_nickname_lower ON users.users (lower(nickname));
