package rk181.java_survey_app_backend.config.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import rk181.java_survey_app_backend.auth.AuthService;
import rk181.java_survey_app_backend.users.User;

@Component
public class BearerTokenAuthFilter extends OncePerRequestFilter {

    private final AuthService authService;
    private final String HEADER = "Authorization";
    private final String PREFIX = "Bearer ";

    public BearerTokenAuthFilter(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Check if token is valid using AuthService
     * @param accessToken
     * @return User or null if token is invalid
     */
    private User isTokenValid(String accessToken) {
        return authService.checkToken(accessToken);
    }

    /**
     * Set authentication context
     * @param user
     */
    private void setAuthContext(User user) {
        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(user.getId(), null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HEADER);

        if(authHeader != null && authHeader.startsWith(PREFIX) && !authHeader.substring(7).isBlank()) {
            String accessToken = authHeader.substring(7);
            User user = isTokenValid(accessToken);

            if(user == null) {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            else {
                setAuthContext(user);
                //Authentication authenticationToken = new UsernamePasswordAuthenticationToken(user, null, null);
                //SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}