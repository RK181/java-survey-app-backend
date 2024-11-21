package rk181.java_survey_app_backend.users;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import rk181.java_survey_app_backend.users.dto.UserDTO;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // Can be substituted with @RequiredArgsConstructor of Lombok
    // This way we have more visual legibility
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUser() {
        return userService.getAuthUser();
    }

    @GetMapping("/{nickname}/surveys")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserAndSurveys(@PathVariable String nickname) {
        return userService.getUserAndSurveys(nickname);
    }

    @GetMapping("/surveys")
    @ResponseStatus(HttpStatus.OK)
    public UserDTO getUserAndSurveys() {
        return userService.getAuthUserAndSurveys();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete() {
        userService.delete();
    }
}
