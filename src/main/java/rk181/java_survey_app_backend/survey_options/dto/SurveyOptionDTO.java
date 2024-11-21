package rk181.java_survey_app_backend.survey_options.dto;

import lombok.Data;
import rk181.java_survey_app_backend.survey_options.SurveyOption;

@Data
public class SurveyOptionDTO {
    private String name;
    private Integer total_votes;

    public SurveyOptionDTO() {}

    public SurveyOptionDTO(String name, Integer total_votes) {
        this.name = name;
        this.total_votes = total_votes;
    }

    public SurveyOptionDTO(SurveyOption surveyOption) {
        this.name = surveyOption.getName();
        this.total_votes = surveyOption.getTotal_votes();
    }
}
