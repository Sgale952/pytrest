CREATE TABLE images (
    image_id BIGSERIAL PRIMARY KEY,
    owner VARCHAR(30) NOT NULL,
    name VARCHAR(20) NOT NULL,
    category VARCHAR(15) NOT NULL,
    password VARCHAR(15) NOT NULL,
    create_date TIMESTAMP,

    FOREIGN KEY (owner) REFERENCES users(email) ON DELETE CASCADE,
    FOREIGN KEY (category) REFERENCES categories(category) ON DELETE RESTRICT ON UPDATE CASCADE
);