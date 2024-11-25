package rk181.java_survey_app_backend.users;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import rk181.java_survey_app_backend.auth.Auth;
import rk181.java_survey_app_backend.surveys.Survey;
import rk181.java_survey_app_backend.users.dto.UserDTO;

@Service
public class UserService {

    private final UserRepository userRepository;

    // Can be replaced with @RequiredArgsConstructor of Lombok
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

    /**
     * Delete the user from the database
     */
    public void delete() {
        Long id = Auth.getUserIDFromContext();
        userRepository.deleteById(id);
    }

    /**
     * Get the authenticated user
     * @return The user
     */
    @Transactional(readOnly = true)
    public UserDTO getAuthUser() {
        Long id = Auth.getUserIDFromContext();
        User user = userRepository.getReferenceById(id);
        
        return new UserDTO(user.getNickname(), null);
    }

    /**
     * Get the authenticated user and his surveys. Offset and limit can be provided. Default limit is 100
     * @return The user and the surveys array with offset and limit. Default limit is 100
     */
    @Transactional(readOnly = true)
    public UserDTO getAuthUserAndSurveys(Integer offset, Integer limit) {
        Long id = Auth.getUserIDFromContext();
        User user = userRepository.getReferenceById(id);

        List<Survey> surveys = user.getSurveys().stream()
            .skip(offset != null ? offset : 0)
            .limit(limit != null && limit != 0? limit : 100)
            .toList();

        return new UserDTO(user.getNickname(), surveys);
    }

    /**
     * Get the user and his surveys by nickname
     * @throws ResponseStatusException NOT_FOUND if user not found
     * @return The user and the surveys array with offset and limit. Default limit is 100
     */
    @Transactional(readOnly = true)
    public UserDTO getUserAndSurveys(String nickname, Integer offset, Integer limit) {
        User user = userRepository.findByNickname(nickname).orElse(null);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }

        List<Survey> surveys = user.getSurveys().stream()
            .skip(offset != null ? offset : 0)
            .limit(limit != null && limit != 0? limit : 100)
            .toList();
        

        return new UserDTO(user.getNickname(), surveys);
    }
}
