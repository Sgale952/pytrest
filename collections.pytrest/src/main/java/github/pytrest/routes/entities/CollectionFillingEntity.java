package github.pytrest.routes.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.springframework.data.annotation.Id;

@Entity
@Table(name = "collections_filling")
public class CollectionFillingEntity {
    @Id
    private long collectionId;
    private long imageId;

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
