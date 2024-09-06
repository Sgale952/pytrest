package github.pytrest.routes.models;

import github.pytrest.routes.entities.User;

import java.util.List;

public record RegistrationModel(User user, List<String> authorities) {
}
