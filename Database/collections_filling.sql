CREATE TABLE collections_filling (
    collection_id BIGINT,
    image_id BIGINT,

    FOREIGN KEY (collection_id) REFERENCES collections(collection_id) ON DELETE CASCADE,
    FOREIGN KEY (image_id) REFERENCES images(image_id) ON DELETE CASCADE
);