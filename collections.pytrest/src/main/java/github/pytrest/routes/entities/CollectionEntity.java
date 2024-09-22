package github.pytrest.routes.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "collections")
public class CollectionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long collectionId;
    private String owner;
    private String name;
    @Column(name = "create_date", updatable = false, insertable = false)
    private LocalDateTime createDate;

    public CollectionEntity() {};

    public CollectionEntity(String owner, String name) {
        this.owner = owner;
        this.name = name;
    }

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
