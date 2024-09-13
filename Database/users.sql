CREATE TABLE users (
    username VARCHAR(20) NOT NULL PRIMARY KEY,
    password TEXT NOT NULL,
    enabled BOOLEAN NOT NULL
);