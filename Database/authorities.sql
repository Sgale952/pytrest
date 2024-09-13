CREATE TABLE authorities (
    username VARCHAR(20) NOT NULL,
    authority VARCHAR(20) NOT NULL,

    CONSTRAINT fk_authorities_users FOREIGN KEY (username) REFERENCES users (username) ON DELETE CASCADE
);

CREATE UNIQUE INDEX ix_auth_username ON authorities (username, authority);