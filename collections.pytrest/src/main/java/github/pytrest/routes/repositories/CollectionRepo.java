package github.pytrest.routes.repositories;

import github.pytrest.routes.entities.CollectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionRepo extends JpaRepository<CollectionEntity, Long> {
}
