package github.pytrest.routes.models;

import java.util.List;

public record LoginResponse(String username, List<String> roles, String jwtToken) {
}
