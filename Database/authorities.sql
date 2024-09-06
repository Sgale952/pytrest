CREATE TABLE authorities (
    owner VARCHAR(30) NOT NULL,
    authority VARCHAR(10) NOT NULL,

    FOREIGN KEY (owner) REFERENCES users(email) ON DELETE CASCADE
);
