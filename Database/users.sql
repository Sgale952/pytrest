CREATE TABLE users (
    email VARCHAR(30) PRIMARY KEY,
    password VARCHAR(64) NOT NULL,
    username VARCHAR(20) NOT NULL,
    avatar_id BIGINT NOT NULL DEFAULT 0,

    FOREIGN KEY (avatar_id) REFERENCES images(image_id) ON DELETE SET DEFAULT
);
