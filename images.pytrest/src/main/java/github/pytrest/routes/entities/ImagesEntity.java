package github.pytrest.routes.entities;

import java.time.LocalDateTime;

import github.pytrest.routes.models.ImageCategories;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "images")
public class ImagesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long imageId;
    private String owner;
    private String name;
    private String category;
    @Column(name = "create_date", updatable = false, insertable = false)
    private LocalDateTime createDate;

    public ImagesEntity() {}
    public ImagesEntity(String owner, String name, ImageCategories category) {
        this.owner = owner;
        this.name = name;
        this.category = category.toString();
    }

    public long getImageId() {
        return imageId;
    }
    public void setImageId(long imageId) {
        this.imageId = imageId;
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

    public ImageCategories getCategory() {
        return ImageCategories.toImageCategories(category);
    }
    public void setCategory(ImageCategories category) {
        this.category = category.toString();
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }
}
