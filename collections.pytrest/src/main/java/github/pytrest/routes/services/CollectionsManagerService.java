package github.pytrest.routes.services;

import org.springframework.stereotype.Service;

import github.pytrest.routes.entities.CollectionEntity;
import github.pytrest.routes.repositories.CollectionFillingRepo;
import github.pytrest.routes.repositories.CollectionRepo;

@Service
public class CollectionsManagerService {
    private final CollectionRepo collectionRepo;
    private final CollectionFillingRepo collectionFillingRepo;

    public CollectionsManagerService(CollectionRepo collectionRepo, CollectionFillingRepo collectionFillingRepo) {
        this.collectionRepo = collectionRepo;
        this.collectionFillingRepo = collectionFillingRepo;
    }

    public long create(String username, String collectionName) {
        CollectionEntity entity = new CollectionEntity(username, collectionName);
        entity = collectionRepo.save(entity);
        return entity.getCollectionId();
    }
}
