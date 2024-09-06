CREATE TABLE images_tags (
    image_id BIGINT,
    tag_id VARCHAR(15),

    FOREIGN KEY (image_id) REFERENCES images(image_id) ON DELETE CASCADE,
    FOREIGN KEY (tag_id) REFERENCES tags(tag) ON DELETE RESTRICT ON UPDATE CASCADE
);