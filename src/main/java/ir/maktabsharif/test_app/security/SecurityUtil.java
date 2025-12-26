package ir.maktabsharif.test_app.security;

import ir.maktabsharif.test_app.model.User;
import ir.maktabsharif.test_app.repository.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    private final UserRepository userRepository;

    public SecurityUtil(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public  User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AccessDeniedException("Unauthenticated access");
        }

        Object principal = authentication.getPrincipal();

        String username;

        if (principal instanceof UserDetails userDetails) {
            username = userDetails.getUsername();
        } else if (principal instanceof String) {
            throw new AccessDeniedException("Anonymous user");
        } else {
            throw new AccessDeniedException("Invalid authentication principal");
        }

        return userRepository.findByEmail(username)
                .orElseThrow(() ->
                        new AccessDeniedException("User not found in database")
                );
    }
}
