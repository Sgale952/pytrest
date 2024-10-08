package github.pytrest.routes.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "collections_filling")
public class CollectionFillingEntity {
    @Id
    private long collectionId;
    private long imageId;

    public CollectionFillingEntity() {}
    public CollectionFillingEntity(long collectionId, long imageId) {
        this.collectionId = collectionId;
        this.imageId = imageId;
    }

    public long getCollectionId() {
        return collectionId;
    }
    public void setCollectionId(long collectionId) {
        this.collectionId = collectionId;
    }

    public long getImageId() {
        return imageId;
    }
    public void setImageId(long imageId) {
        this.imageId = imageId;
    }
}
