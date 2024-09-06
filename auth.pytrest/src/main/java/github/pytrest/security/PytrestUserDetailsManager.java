package github.pytrest.security;

import github.pytrest.routes.entities.Authorities;
import github.pytrest.routes.entities.User;
import github.pytrest.routes.repositories.AuthoritiesRepo;
import github.pytrest.routes.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;

import java.util.Collection;
import java.util.stream.Collectors;

public class PytrestUserDetailsManager implements UserDetailsManager {
    private final UserRepo userRepo;
    private final AuthoritiesRepo authoritiesRepo;
    private final PasswordEncoder passwordEncoder;

    public PytrestUserDetailsManager(UserRepo userRepo, AuthoritiesRepo authoritiesRepo, PasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.authoritiesRepo = authoritiesRepo;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Override
    public void createUser(UserDetails userDetails) {
        PytrestUserDetails pytrestUserDetails = from(userDetails);

        updateUser(pytrestUserDetails);
        updateAuthorities(pytrestUserDetails);
    }

    @Override
    public void updateUser(UserDetails userDetails) {
        PytrestUserDetails pytrestUserDetails = from(userDetails);

        updateUser(pytrestUserDetails);
        updateAuthorities(pytrestUserDetails);
    }

    @Override
    public void deleteUser(String email) {
        userRepo.deleteById(email);
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) {
        PytrestAuthenticationToken currentUser = from(SecurityContextHolder.getContext().getAuthentication());

        String email = currentUser.getEmail();
        User user = userRepo.findById(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Old password is incorrect");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
    }

    @Override
    public boolean userExists(String email) {
        return userRepo.existsById(email);
    }

    @Override
    @Deprecated //The email is used as the user id
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return loadUserByEmail(email);
    }

    public PytrestUserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        User user = userRepo.findById(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found by email: " + email));

        return new PytrestUserDetails(user, getAuthorities(email));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(String email) {
        return authoritiesRepo.findAllByOwner(email).stream()
                .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
                .collect(Collectors.toList());
    }

    private PytrestUserDetails from(UserDetails userDetails) {
        if (userDetails instanceof PytrestUserDetails)
            return (PytrestUserDetails) userDetails;
        throw new IllegalArgumentException("UserDetails must be instance of PytrestUserDetails");
    }

    private PytrestAuthenticationToken from(Authentication userAuthenticationToken) {
        if (userAuthenticationToken instanceof PytrestAuthenticationToken)
            return (PytrestAuthenticationToken) userAuthenticationToken;
        throw new IllegalArgumentException("userAuthenticationToken must be instance of UsernamePasswordAuthenticationToken");
    }

    //TODO: replace setPassword to changePassword()
    private void updateUser(PytrestUserDetails pytrestUserDetails) {
        String email = pytrestUserDetails.getEmail();
        User user = userRepo.findById(email).orElse(new User());

        user.setEmail(pytrestUserDetails.getEmail());
        user.setPassword(passwordEncoder.encode(pytrestUserDetails.getPassword()));
        user.setUsername(pytrestUserDetails.getUsername());
        user.setAvatarId(pytrestUserDetails.getAvatarId());
        userRepo.save(user);
    }

    private void updateAuthorities(PytrestUserDetails pytrestUserDetails) {
        String email = pytrestUserDetails.getEmail();
        Collection<? extends GrantedAuthority> newAuthorities = pytrestUserDetails.getAuthorities();

        authoritiesRepo.deleteById(email);
        for (GrantedAuthority authority : newAuthorities) {
            Authorities entity = new Authorities();
            entity.setOwner(email);
            entity.setAuthority(authority.toString());
            authoritiesRepo.save(entity);
        }
    }
}
