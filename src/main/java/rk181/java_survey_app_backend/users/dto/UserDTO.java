package rk181.java_survey_app_backend.users.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import rk181.java_survey_app_backend.surveys.Survey;
import rk181.java_survey_app_backend.surveys.dto.SurveyDTO;

@JsonInclude(Include.NON_NULL)
@Data
public class UserDTO {

    @NotBlank
    @Size(min = 4, max = 20, message = "Nickname must be between 4 and 20 characters")
    private String nickname;
    /**
     * The surveys created by this user, ignored in JSON if null
     */
    private List<SurveyDTO> surveys = null;

    public UserDTO() {}

    public UserDTO(String nickname, List<Survey> surveys) {
        this.nickname = nickname;
        if (surveys != null){
            if (!surveys.isEmpty()) {
                this.surveys = surveys.stream().map(survey -> new SurveyDTO(survey, null)).toList();
            } else {
                this.surveys = new ArrayList<>();
            }
        }
    }
}
