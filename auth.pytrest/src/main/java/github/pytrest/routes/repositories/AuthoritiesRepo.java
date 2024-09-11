package github.pytrest.routes.repositories;


import github.pytrest.routes.entities.AuthoritiesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthoritiesRepo extends JpaRepository<AuthoritiesEntity, String> {
    List<AuthoritiesEntity> findAllByOwner(String email);
}
