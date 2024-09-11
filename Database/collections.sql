CREATE TABLE collections (
    collection_id BIGSERIAL PRIMARY KEY,
    owner VARCHAR(30) NOT NULL,
    name VARCHAR(20) NOT NULL,
    create_date TIMESTAMP,

    FOREIGN KEY (owner) REFERENCES users(username) ON DELETE CASCADE
);