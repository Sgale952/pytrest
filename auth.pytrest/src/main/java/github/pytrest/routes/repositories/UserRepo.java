package github.pytrest.routes.repositories;

import github.pytrest.routes.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, String> {
}
