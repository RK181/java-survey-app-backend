package rk181.java_survey_app_backend.surveys;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import jakarta.transaction.Transactional;
import rk181.java_survey_app_backend.auth.Auth;
import rk181.java_survey_app_backend.surveys.dto.SurveyDTO;
import rk181.java_survey_app_backend.users.User;
import rk181.java_survey_app_backend.users.UserRepository;

@Service
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final UserRepository userRepository;


    public SurveyService(SurveyRepository surveyRepository, UserRepository userRepository) {
        this.surveyRepository = surveyRepository;
        this.userRepository = userRepository;
    }

    public SurveyDTO createSurvey(SurveyDTO surveyDTO) {
        User authUser = userRepository.findById(Auth.getUserIDFromContext()).orElse(null);
        if (authUser == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found");
        }
        
        Survey survey = Survey.fromDTO(authUser, surveyDTO);
        surveyRepository.save(survey);

        return new SurveyDTO(survey, survey.getUser());
    }

    public SurveyDTO getSurveyById(Long survey_id) {
        Survey survey = surveyRepository.findById(survey_id).orElse(null);
        if (survey == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Survey not found");
        }
        
        return new SurveyDTO(survey, survey.getSurveyOptions(), survey.getUser());    
    }

    public List<SurveyDTO> getAllSurveys() {
        List<Survey> surveys = surveyRepository.findAll();

        return SurveyDTO.listFromSurveys(surveys);
    }

    @Transactional
    public SurveyDTO putSurveyById(Long survey_id, SurveyDTO surveyDTO) {
        Long authUserID = Auth.getUserIDFromContext();
        Survey survey = surveyRepository.findById(survey_id).orElse(null);
        if (survey == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Survey not found");
        }
        if (!survey.getUser().getId().equals(authUserID)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not the owner");
        }

        survey.setTitle(surveyDTO.getTitle());
        survey.setDescription(surveyDTO.getDescription());
        surveyRepository.save(survey);

        return new SurveyDTO(survey, survey.getUser());
    }

    public void deleteSurveyById(Long survey_id) {
        Long authUserID = Auth.getUserIDFromContext();
        Survey survey = surveyRepository.findById(survey_id).orElse(null);
        if (survey == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Survey not found");
        }
        if (!survey.getUser().getId().equals(authUserID)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "User is not the owner");
        }
        surveyRepository.deleteById(survey_id);
    }

    public void deleteSurvey(Long survey_id) {
        surveyRepository.deleteById(survey_id);
    }
}
