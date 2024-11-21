package rk181.java_survey_app_backend.survey_options;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import rk181.java_survey_app_backend.auth.Auth;
import rk181.java_survey_app_backend.survey_options.dto.SurveyOptionDTO;
import rk181.java_survey_app_backend.survey_options_users.SurveyOptionsUsers;
import rk181.java_survey_app_backend.survey_options_users.SurveyOptionsUsersPK;
import rk181.java_survey_app_backend.survey_options_users.SurveyOptionsUsersRepository;
import rk181.java_survey_app_backend.surveys.Survey;
import rk181.java_survey_app_backend.surveys.SurveyRepository;
import rk181.java_survey_app_backend.users.User;
import rk181.java_survey_app_backend.users.UserRepository;

@Service
public class SurveyOptionService {

    private final SurveyOptionRepository surveyOptionRepository;
    private final SurveyRepository surveyRepository;
    private final UserRepository userRepository;
    private final SurveyOptionsUsersRepository surveyOptionsUsersRepository;

    public SurveyOptionService(SurveyOptionRepository surveyOptionRepository, SurveyRepository surveyRepository, UserRepository userRepository, SurveyOptionsUsersRepository surveyOptionsUsersRepository) {
        this.surveyOptionRepository = surveyOptionRepository;
        this.surveyRepository = surveyRepository;
        this.userRepository = userRepository;
        this.surveyOptionsUsersRepository = surveyOptionsUsersRepository;
    }

    public SurveyOptionDTO createSurveyOption(Long survey_id, SurveyOptionDTO surveyOptionDTO) {
        Long authUserID = Auth.getUserIDFromContext();
        Survey survey = surveyRepository.findById(survey_id).orElse(null);
        if (survey == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Survey not found");
        }
        if (!survey.getUser().getId().equals(authUserID)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not authorized to create survey option");
        }

        SurveyOption surveyOption = SurveyOption.fromDTO(surveyOptionDTO, survey);
        surveyOptionRepository.save(surveyOption);

        return new SurveyOptionDTO(surveyOption);
    }

    public SurveyOptionDTO vote(Long survey_id, Long id) {
        Long authUserID = Auth.getUserIDFromContext();
        Survey survey = surveyRepository.findById(survey_id).orElse(null);
        if (survey == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Survey not found");
        }
        
        SurveyOption surveyOption = surveyOptionRepository.findById(id).orElse(null);
        if (surveyOption == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Survey option not found");
        }
        
        User user = userRepository.findById(authUserID).orElse(null);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized user");
        }

        SurveyOptionsUsersPK composite_id = new SurveyOptionsUsersPK(user.getId(), surveyOption.getId());
        SurveyOptionsUsers surveyOptionsUsers = new SurveyOptionsUsers(composite_id);
        if (surveyOptionsUsersRepository.existsById(composite_id)) {
            surveyOptionsUsersRepository.delete(surveyOptionsUsers);
            surveyOption.setTotal_votes(surveyOption.getTotal_votes() - 1);
        }
        else {
            surveyOptionsUsersRepository.save(surveyOptionsUsers);
            surveyOption.setTotal_votes(surveyOption.getTotal_votes() + 1);
        }

        /*List<User> votedUsers = surveyOption.getUsers();
        if (votedUsers.contains(user)) {
            votedUsers.remove(user);
            user.getSurveyOption().remove(surveyOption);
            //surveyOption.setTotal_votes(surveyOption.getTotal_votes() - 1);

        }
        else {
            votedUsers.add(user);
            user.getSurveyOption().add(surveyOption);
            //surveyOption.setTotal_votes(surveyOption.getTotal_votes() + 1);
        }
        surveyOptionRepository.flush();*/

        return new SurveyOptionDTO(surveyOption);
    }

}
