package rk181.java_survey_app_backend.users;

import org.springframework.stereotype.Service;
import rk181.java_survey_app_backend.auth.Auth;

@Service
public class UserService {

    private final UserRepository userRepository;

    // Can be substituted with @RequiredArgsConstructor of Lombok
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /***
     * Get user by nickname
     * @param nickname
     * @return User or null if not found
     */
    public User getUserByNickname(String nickname) {
        return userRepository.findByNickname(nickname).orElse(null);
    }

    public void delete() {
        Long id = Auth.getUserIDFromContext();
        userRepository.deleteById(id);
    }
}
