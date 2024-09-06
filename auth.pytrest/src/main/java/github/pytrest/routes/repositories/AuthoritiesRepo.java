package github.pytrest.routes.repositories;


import github.pytrest.routes.entities.Authorities;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuthoritiesRepo extends JpaRepository<Authorities, String> {
    List<Authorities> findAllByOwner(String email);
}
