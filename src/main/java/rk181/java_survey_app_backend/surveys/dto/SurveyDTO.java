package rk181.java_survey_app_backend.surveys.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import rk181.java_survey_app_backend.survey_options.SurveyOption;
import rk181.java_survey_app_backend.survey_options.dto.SurveyOptionDTO;
import rk181.java_survey_app_backend.surveys.Survey;
import rk181.java_survey_app_backend.users.User;
import rk181.java_survey_app_backend.users.dto.UserDTO;

@JsonInclude(Include.NON_NULL)
@Data
public class SurveyDTO {

    private Long id;
    private String title;
    private String description;
    private Integer total_votes;
    private UserDTO user = null;
    private List<SurveyOptionDTO> surveyOptions = null;
    

    public SurveyDTO() {}

    public SurveyDTO(Survey survey) {
        this.id = survey.getId();
        this.title = survey.getTitle();
        this.description = survey.getDescription();
        this.total_votes = survey.getTotal_votes();
    }

    public SurveyDTO(Survey survey, User user) {
        this.id = survey.getId();
        this.title = survey.getTitle();
        this.description = survey.getDescription();
        this.total_votes = survey.getTotal_votes();
        if (user != null)
            this.user = new UserDTO(user.getNickname(), null);
    }

    public SurveyDTO(Survey survey, List<SurveyOption> surveyOptions, User user) {
        this.id = survey.getId();
        this.title = survey.getTitle();
        this.description = survey.getDescription();
        this.total_votes = survey.getTotal_votes();
        if (surveyOptions != null)
            this.surveyOptions = surveyOptions.isEmpty() ? new ArrayList<>() : surveyOptions.stream().map(surveyOption -> new SurveyOptionDTO(surveyOption)).toList();
        if (user != null)
            this.user = new UserDTO(user.getNickname(), null);
    }

    public static List<SurveyDTO> listFromSurveys(List<Survey> surveys) {
        return surveys.stream().map(survey -> new SurveyDTO(survey, survey.getUser())).toList();
    }
    
}
