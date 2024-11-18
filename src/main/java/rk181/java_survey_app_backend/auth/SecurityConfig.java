package rk181.java_survey_app_backend.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import jakarta.servlet.DispatcherType;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final BearerTokenAuthFilter bearerTokenAuthFilter;
    //private final AuthenticationEntryPoint authEntryPoint;

    public SecurityConfig(BearerTokenAuthFilter bearerTokenAuthFilter) {
        this.bearerTokenAuthFilter = bearerTokenAuthFilter;
        //this.authEntryPoint = authEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests(request -> {
                request.dispatcherTypeMatchers(DispatcherType.ERROR).permitAll(); //requests.requestMatchers("/error").permitAll();
                request.requestMatchers("/auth/register", "/auth/login").permitAll();
                request.anyRequest().authenticated();
            })
            .addFilterAfter(bearerTokenAuthFilter, BasicAuthenticationFilter.class)
            .cors(withDefaults())
            .csrf(csrf -> csrf.disable())
            .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        
        // Add custom authentication entry point
        //http.exceptionHandling(handling -> handling.authenticationEntryPoint(authEntryPoint));
        
        return http.build();
    }
}
