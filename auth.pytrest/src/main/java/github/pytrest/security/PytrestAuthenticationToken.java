package github.pytrest.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class PytrestAuthenticationToken extends UsernamePasswordAuthenticationToken {
    private final String email;

    public PytrestAuthenticationToken(Object principal, Object credentials, PytrestUserDetails userDetails) {
        super(principal, credentials);
        this.email = userDetails.getEmail();
    }

    public String getEmail() {
        return email;
    }
}
