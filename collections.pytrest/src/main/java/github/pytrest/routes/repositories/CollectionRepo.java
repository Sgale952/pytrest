package github.pytrest.routes.repositories;

import github.pytrest.routes.entities.CollectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CollectionRepo extends JpaRepository<CollectionEntity, Long> {
    @Query("SELECT c FROM CollectionEntity c WHERE c.owner = :username")
    CollectionEntity[] getUserCollections(@Param("username") String username);
}
