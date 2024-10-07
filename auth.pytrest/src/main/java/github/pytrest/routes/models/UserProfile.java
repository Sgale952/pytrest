package github.pytrest.routes.models;

import java.util.List;

public record UserProfile(String nickname, long avatarId, List<?> collections) {
}
