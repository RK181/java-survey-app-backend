package rk181.java_survey_app_backend.auth;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;


public abstract class Auth {
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