package rk181.java_survey_app_backend.users;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // Can be substituted with @RequiredArgsConstructor of Lombok
    // This way we have more visual legibility
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete() {
        userService.delete();
    }
}
