package rk181.java_survey_app_backend.surveys;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import org.springframework.transaction.annotation.Transactional;
import rk181.java_survey_app_backend.auth.Auth;
import rk181.java_survey_app_backend.surveys.dto.SurveyDTO;
import rk181.java_survey_app_backend.users.User;
import rk181.java_survey_app_backend.users.UserRepository;

@Service
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final UserRepository userRepository;

    // Can be replaced with @RequiredArgsConstructor of Lombok
    public SurveyService(SurveyRepository surveyRepository, UserRepository userRepository) {
        this.surveyRepository = surveyRepository;
        this.userRepository = userRepository;
    }

    /**
     * Create a survey
     * @param surveyDTO
     * @return SurveyDTO with user(owner)
     */
    public SurveyDTO createSurvey(SurveyDTO surveyDTO) {
        User authUser = userRepository.getReferenceById(Auth.getUserIDFromContext());
        
        Survey survey = Survey.fromDTO(authUser, surveyDTO);
        surveyRepository.save(survey);

        return new SurveyDTO(survey, survey.getUser());
    }

    /**
     * Get a survey by id
     * @param surveyId
     * @throws ResponseStatusException NOT_FOUND if survey not found
     * @return SurveyDTO with user(owner) and survey options
     */
    @Transactional(readOnly = true)
    public SurveyDTO getSurveyById(Long surveyId) {
        Survey survey = surveyRepository.findById(surveyId).orElse(null);
        if (survey == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Survey not found");
        }
        
        return new SurveyDTO(survey, survey.getSurveyOptions(), survey.getUser());    
    }

    /**
     * Get all surveys
     * @return List of SurveyDTO with user(owner)
     */
    @Transactional(readOnly = true)
    public List<SurveyDTO> getAllSurveys() {
        List<Survey> surveys = surveyRepository.findAll();
        return SurveyDTO.listFromSurveys(surveys);
    }

    /**
     * Update a survey by id
     * @param surveyId
     * @param surveyDTO
     * @throws ResponseStatusException NOT_FOUND if survey not found or user is not the owner
     * @return SurveyDTO with updated fields and user
     */
    @Transactional
    public SurveyDTO putSurveyById(Long surveyId, SurveyDTO surveyDTO) {
        Long authUserID = Auth.getUserIDFromContext();
        Survey survey = surveyRepository.findById(surveyId).orElse(null);
        if (survey == null || !survey.getUser().getId().equals(authUserID)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Survey not found");
        }

        survey.setTitle(surveyDTO.getTitle());
        survey.setDescription(surveyDTO.getDescription());
        surveyRepository.save(survey);

        return new SurveyDTO(survey, survey.getUser());
    }

    /**
     * Delete a survey by id
     * @param surveyId
     * @throws ResponseStatusException NOT_FOUND if survey not found or user is not the owner
     */
    public void deleteSurveyById(Long surveyId) {
        Long authUserID = Auth.getUserIDFromContext();
        if (!surveyRepository.existsByUserIdAndId(authUserID, surveyId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Survey not found");
        }
        surveyRepository.deleteById(surveyId);
    }
}
