package github.pytrest.routes.repositories;

import github.pytrest.routes.entities.UserProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepo extends JpaRepository<UserProfileEntity, String> {
}
