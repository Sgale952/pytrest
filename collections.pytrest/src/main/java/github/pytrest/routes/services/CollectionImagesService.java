package github.pytrest.routes.services;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import github.pytrest.routes.entities.CollectionEntity;
import github.pytrest.routes.entities.CollectionFillingEntity;
import github.pytrest.routes.repositories.CollectionFillingRepo;
import github.pytrest.routes.repositories.CollectionRepo;

@Service
public class CollectionImagesService {
    private final CollectionFillingRepo fillingRepo;
    private final CollectionRepo collectionRepo;

    public CollectionImagesService(CollectionFillingRepo fillingRepo, CollectionRepo collectionRepo) {
        this.fillingRepo = fillingRepo;
        this.collectionRepo = collectionRepo;
    }

    public void addImamageToCollection(String owner, long collectionId, long imageId) {
        checkCollectionOwner(owner, collectionId);

        CollectionFillingEntity fillingEntity = new CollectionFillingEntity(collectionId, imageId);
        fillingRepo.save(fillingEntity);
    }

    public void removeImamageFromCollection(String owner, long collectionId, long imageId) {
        checkCollectionOwner(owner, collectionId);
        
        CollectionFillingEntity fillingEntity = new CollectionFillingEntity(collectionId, imageId);
        fillingRepo.delete(fillingEntity);
    }

    public long[] getImages(long collectionId, int page) {
        Pageable pageable = PageRequest.of(page, 50);
        Page<CollectionFillingEntity> pages = fillingRepo.findByCollectionId(collectionId, pageable);

        return pages.getContent()
            .stream()
            .mapToLong(CollectionFillingEntity::getImageId)
            .toArray();
    }

    private void checkCollectionOwner(String owner, long collectionId) {
        CollectionEntity collection = collectionRepo.findById(collectionId).orElseThrow();
        if (!collection.getOwner().equals(owner)) {
            throw new RuntimeException("User " + owner + " is not own this collection");
        }
    }
}
