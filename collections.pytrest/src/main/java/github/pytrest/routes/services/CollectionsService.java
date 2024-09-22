package github.pytrest.routes.services;

import org.bouncycastle.crypto.RuntimeCryptoException;
import org.springframework.stereotype.Service;

import github.pytrest.routes.entities.CollectionEntity;
import github.pytrest.routes.repositories.CollectionRepo;

@Service
public class CollectionsService {
    private final CollectionRepo collectionRepo;

    public CollectionsService(CollectionRepo collectionRepo) {
        this.collectionRepo = collectionRepo;
    }

    public long create(String owner, String collectionName) {
        CollectionEntity entity = new CollectionEntity(owner, collectionName);
        entity = collectionRepo.save(entity);
        return entity.getCollectionId();
    }

    public void delete(String owner, long id) {
        CollectionEntity deletedCollection = collectionRepo.findById(id).orElseThrow();
        checkOwner(deletedCollection, owner);
        collectionRepo.delete(deletedCollection);
    }

    public void changeName(String owner, long id, String newName) {
        CollectionEntity updatedCollections = collectionRepo.findById(id).orElseThrow();
        checkOwner(updatedCollections, owner);
        updatedCollections.setName(newName);
        collectionRepo.save(updatedCollections);
    }

    public CollectionEntity getCollection(long id) {
        return collectionRepo.findById(id).orElseThrow();
    }

    public CollectionEntity[] getUserCollections(String owner) {
        return collectionRepo.getUserCollections(owner);
    }

    private void checkOwner(CollectionEntity collection, String owner) {
        if (!collection.getOwner().equals(owner)) {
            throw new RuntimeException("User " + owner + " is not own this collection");
        }
    }
}
