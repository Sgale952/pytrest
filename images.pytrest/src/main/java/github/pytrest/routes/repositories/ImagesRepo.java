package github.pytrest.routes.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import github.pytrest.routes.entities.ImagesEntity;

public interface ImagesRepo extends JpaRepository<ImagesEntity, Long> {
}
