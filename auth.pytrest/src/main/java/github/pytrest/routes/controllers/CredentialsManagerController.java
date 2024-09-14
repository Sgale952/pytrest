package github.pytrest.routes.controllers;

import github.pytrest.routes.entities.UserProfileEntity;
import github.pytrest.routes.models.ChangePassword;
import github.pytrest.routes.repositories.UserProfileRepo;
import github.pytrest.security.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController()
@RequestMapping("/auth")
public class CredentialsManagerController {
    private final UserProfileRepo userProfileRepo;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final JdbcUserDetailsManager userDetailsManager;

    public CredentialsManagerController(UserProfileRepo userProfileRepo, JwtUtils jwtUtils, PasswordEncoder passwordEncoder, JdbcUserDetailsManager userDetailsManager) {
        this.userProfileRepo = userProfileRepo;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.userDetailsManager = userDetailsManager;
    }

    @PostMapping("/change-password")
    private void changePassword(@RequestBody ChangePassword passwords) {
        userDetailsManager.changePassword(
                passwordEncoder.encode(passwords.oldPassword()), passwordEncoder.encode(passwords.newPassword()));
    }

    @PostMapping("/change-image")
    private void changeImage(@RequestBody String newAvatarId) {
        String username = getCurrentUsername();
        UserProfileEntity userProfileEntity = userProfileRepo.findById(username).orElseThrow();
        userProfileEntity.setAvatarId(Long.parseLong(newAvatarId));
        userProfileRepo.save(userProfileEntity);
    }

    @PostMapping("/change-nickname")
    private void changeNickname(@RequestBody String newNickname) {
        String username = getCurrentUsername();
        UserProfileEntity userProfileEntity = userProfileRepo.findById(username).orElseThrow();
        userProfileEntity.setNickname(newNickname);
        userProfileRepo.save(userProfileEntity);
    }

    private String getCurrentUsername() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String jwt = jwtUtils.getJwtFromHeader(request);
        return jwtUtils.getUsernameFromJwtToken(jwt);
    }
}
