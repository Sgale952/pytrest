package github.pytrest.routes.controllers;

import github.pytrest.routes.models.LoginRequest;
import github.pytrest.routes.models.LoginResponse;
import github.pytrest.security.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController("/auth")
public class AuthController {
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final JdbcUserDetailsManager userDetailsManager;
    private final AuthenticationManager authenticationManager;

    public AuthController(JwtUtils jwtUtils, PasswordEncoder passwordEncoder, JdbcUserDetailsManager userDetailsManager, AuthenticationManager authenticationManager) {
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsManager = userDetailsManager;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/a")
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("user.getPassword()");
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

            return authenticate(loginRequest);
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
        return authenticate(loginRequest);
    }

    private ResponseEntity<?> authenticate(LoginRequest loginRequest) {
        try {
            Authentication authentication;
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password()));
            return createAuthenticationResponse(authentication);
        }
        catch (AuthenticationException exception) {
            Map<String, Object> map = new HashMap<>();
            map.put("message", "Bad credentials");
            map.put("status", false);
            return new ResponseEntity<Object>(map, HttpStatus.NOT_FOUND);
        }
    }

    private ResponseEntity<?> createAuthenticationResponse(Authentication authentication) {
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        String jwtToken = jwtUtils.generateTokenFromUsername(userDetails);

        LoginResponse response = new LoginResponse(userDetails.getUsername(), roles, jwtToken);
        return ResponseEntity.ok(response);
    }
}