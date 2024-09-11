CREATE TABLE users (
    username TEXT NOT NULL;
    nickname VARCHAR(20) NOT NULL,
    avatar_id BIGINT NOT NULL DEFAULT 0,

    FOREIGN KEY (username) REFERENCES users(username) ON DELETE CASCADE,
    FOREIGN KEY (avatar_id) REFERENCES images(image_id) ON DELETE SET DEFAULT
);