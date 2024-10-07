package github.pytrest.routes.repositories;

import github.pytrest.routes.entities.CollectionFillingEntity;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionFillingRepo extends JpaRepository<CollectionFillingEntity, Long> {
    Page<CollectionFillingEntity> findByCollectionId(long collectionId, Pageable pageable);
}