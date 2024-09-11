package github.pytrest.routes.controllers;

import github.pytrest.routes.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    UserRepo userRepo;
    @Autowired
    PasswordEncoder passwordEncoder;

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/a")
    public ResponseEntity<String> test() {
/*        UserEntity user = new UserEntity("anon@tuta.com", passwordEncoder.encode("1234"), "anon");
        userRepo.save(user);*/
        return ResponseEntity.ok("user.getPassword()");
    }
}