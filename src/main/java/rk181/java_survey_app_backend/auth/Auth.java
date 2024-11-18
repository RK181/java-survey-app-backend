package rk181.java_survey_app_backend.auth;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import rk181.java_survey_app_backend.users.User;

public abstract class Auth {
    /**
     * Set authentication context
     * @param user
     */
    public static void setAuthContext(User user) {
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(user.getId(), null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    /**
     * Get user ID from context
     * @return User ID
     */
    public static Long getUserIDFromContext() {
        if (!(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof Long)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "");
        }

        return (Long)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}