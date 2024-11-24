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

@Service
public class SurveyOptionService {

    private final SurveyOptionRepository surveyOptionRepository;
    private final SurveyRepository surveyRepository;
    private final SurveyOptionsUsersRepository surveyOptionsUsersRepository;

    public SurveyOptionService(SurveyOptionRepository surveyOptionRepository, SurveyRepository surveyRepository, SurveyOptionsUsersRepository surveyOptionsUsersRepository) {
        this.surveyOptionRepository = surveyOptionRepository;
        this.surveyRepository = surveyRepository;
        this.surveyOptionsUsersRepository = surveyOptionsUsersRepository;
    }

    /**
     * Create a survey option for a survey.
     * @param survey_id
     * @param surveyOptionDTO
     * @return SurveyOptionDTO with Survey
     */
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

    /**
     * Vote for a survey option. If the user has already voted, the vote is removed.
     * @param surveyId
     * @param surveyOptionId
     * @throws ResponseStatusException NOT_FOUND if survey option not found 
     * @return SurveyOptionDTO with updated total votes, without Survey
     */
    public SurveyOptionDTO vote(Long surveyId, Long surveyOptionId) {
        Long authUserID = Auth.getUserIDFromContext();
        System.out.println("Get survey option");

        SurveyOption surveyOption = surveyOptionRepository.findBySurveyIdAndId(surveyId, surveyOptionId).orElse(null);
        if (surveyOption == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Survey option not found");
        }

        SurveyOptionsUsersPK compositeId = new SurveyOptionsUsersPK(authUserID, surveyOption.getId());
        SurveyOptionsUsers surveyOptionsUsers = new SurveyOptionsUsers(compositeId);
        if (surveyOptionsUsersRepository.existsById(compositeId)) {
            surveyOptionsUsersRepository.delete(surveyOptionsUsers);
            surveyOption.setTotal_votes(surveyOption.getTotal_votes() - 1);
        }
        else {
            surveyOptionsUsersRepository.save(surveyOptionsUsers);
            surveyOption.setTotal_votes(surveyOption.getTotal_votes() + 1);
        }

        return new SurveyOptionDTO(surveyOption);
    }

    /**
     * Delete a survey option by survey id and survey option id.
     * Also checks if the user is the owner of the survey.
     * @param surveyId
     * @param surveyOptionId
     * @throws ResponseStatusException NOT_FOUND if survey option not found or user is not the owner of the survey
     */
    public void deleteSurveyOptionById(Long surveyId, Long surveyOptionId) {
        Long authUserID = Auth.getUserIDFromContext();
        if (!surveyRepository.existsByUserIdAndId(authUserID, surveyId) &&
            !surveyOptionRepository.existsBySurveyIdAndId(surveyId, surveyOptionId)) 
        {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Survey option not found");
        }
        
        surveyOptionRepository.deleteById(surveyOptionId);
    }

}
