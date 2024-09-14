package github.pytrest.routes.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users_profile")
public class UserProfileEntity {
    @Id
    String username;
    String nickname;
    long avatarId;

    public UserProfileEntity() {}
    public UserProfileEntity(String username, String nickname, long avatarId) {
        this.username = username;
        this.nickname = nickname;
        this.avatarId = avatarId;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public long getAvatarId() {
        return avatarId;
    }
    public void setAvatarId(long avatarId) {
        this.avatarId = avatarId;
    }
}
