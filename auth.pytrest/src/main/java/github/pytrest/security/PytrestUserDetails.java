package github.pytrest.security;

import github.pytrest.routes.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

//@Service
public class PytrestUserDetails implements UserDetails {
    private final String email;
    private final String password;
    private final String username;
    private final Long avatarId;
    private final Collection<? extends GrantedAuthority> authorities;

    public PytrestUserDetails(User user, Collection<? extends GrantedAuthority> authorities) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.username = user.getUsername();
        this.avatarId = user.getAvatarId();
        this.authorities = authorities;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public Long getAvatarId() {
        return avatarId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
