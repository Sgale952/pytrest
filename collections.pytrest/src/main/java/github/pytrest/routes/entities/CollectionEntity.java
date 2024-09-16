package github.pytrest.routes.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Entity
@Table(name = "collections")
public class CollectionEntity {
    @Id
    private long collectionId;
    private String owner;
    private String name;
    private LocalDateTime createDate;

    public long getCollectionId() {
        return collectionId;
    }
    public void setCollectionId(long collectionId) {
        this.collectionId = collectionId;
    }

    public String getOwner() {
        return owner;
    }
    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }
    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
}
