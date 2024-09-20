package github.pytrest.routes.controllers;

import github.pytrest.routes.models.LoginRequest;
import github.pytrest.routes.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController()
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final JdbcUserDetailsManager userDetailsManager;

    public AuthController(PasswordEncoder passwordEncoder, JdbcUserDetailsManager userDetailsManager, AuthService authService) {
        this.authService = authService;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsManager = userDetailsManager;
    }

    @PostMapping("/register")
    private ResponseEntity<?> register(@RequestBody LoginRequest loginRequest) {
        try {
            UserDetails newUser = User
                    .withUsername(loginRequest.username())
                    .password(passwordEncoder.encode(loginRequest.password()))
                    .roles("USER")
                    .build();
            userDetailsManager.createUser(newUser);

            return authService.authenticate(loginRequest);
        }
        catch (Exception e) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", e.getMessage());
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/login")
    private ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        return authService.authenticate(loginRequest);
    }

    @PostMapping("/validate")
    private ResponseEntity<?> validate(@RequestBody String token) {
    return validate(token);
    }
}