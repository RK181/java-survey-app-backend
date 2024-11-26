package rk181.java_survey_app_backend.survey_options.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import rk181.java_survey_app_backend.survey_options.SurveyOption;

@Data
public class SurveyOptionDTO {
    private Long id;
    @NotBlank
    @Size(min = 1, max = 50, message = "Option name must be between 1 and 50 characters")
    private String name;
    /**
     * The total number of votes for this option. Calculated field.
     */
    private Integer total_votes;

    public SurveyOptionDTO() {}

    public SurveyOptionDTO(String name, Integer total_votes) {
        this.name = name;
        this.total_votes = total_votes;
    }

    public SurveyOptionDTO(SurveyOption surveyOption) {
        this.id = surveyOption.getId();
        this.name = surveyOption.getName();
        this.total_votes = surveyOption.getTotal_votes();
    }
}
