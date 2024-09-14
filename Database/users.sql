CREATE TABLE users (
    username VARCHAR(20) NOT NULL PRIMARY KEY,
    password TEXT NOT NULL,
    enabled BOOLEAN NOT NULL
);

CREATE OR REPLACE FUNCTION add_user_profile()
RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO users_profile (username, nickname, avatar_id)
    VALUES (NEW.username, NEW.username, 0);

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER add_profile_after_insert
AFTER INSERT ON users
FOR EACH ROW
EXECUTE FUNCTION add_user_profile();