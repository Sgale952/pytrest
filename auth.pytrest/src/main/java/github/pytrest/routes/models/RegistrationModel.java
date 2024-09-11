package github.pytrest.routes.models;

import github.pytrest.routes.entities.UserEntity;

import java.util.List;

public record RegistrationModel(UserEntity userEntity, List<String> authorities) {
}
