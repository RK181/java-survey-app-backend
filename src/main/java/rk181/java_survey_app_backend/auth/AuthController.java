package rk181.java_survey_app_backend.auth;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import rk181.java_survey_app_backend.auth.dto.AuthDTO;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    // Can be substituted with @RequiredArgsConstructor of Lombok
    // This way we have more visual legibility
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@Valid @RequestBody AuthDTO user) {
        authService.register(user);
    }

	@PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public String login(@Valid @RequestBody AuthDTO user) {
        return authService.login(user);
    }

    @GetMapping("/logout")
    @ResponseStatus(HttpStatus.OK)
    public void logout() {
        authService.logout();
    }
}
