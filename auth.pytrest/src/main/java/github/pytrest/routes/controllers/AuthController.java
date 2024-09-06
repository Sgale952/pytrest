package github.pytrest.routes.controllers;

import github.pytrest.routes.entities.User;
import github.pytrest.routes.models.RegistrationModel;
import github.pytrest.security.PytrestUserDetails;
import github.pytrest.security.PytrestUserDetailsManager;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

@RestController
public class AuthController {

    //@PreAuthorize()
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegistrationModel registrationModel) {
        User user = registrationModel.user();
        Collection<? extends GrantedAuthority> authorities = registrationModel.authorities().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        PytrestUserDetails userDetails = new PytrestUserDetails(user, authorities);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}