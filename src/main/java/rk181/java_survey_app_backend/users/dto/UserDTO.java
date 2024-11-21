package rk181.java_survey_app_backend.users.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;
import rk181.java_survey_app_backend.surveys.Survey;
import rk181.java_survey_app_backend.surveys.dto.SurveyDTO;

@JsonInclude(Include.NON_NULL)
@Data
public class UserDTO {

    private String nickname;
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
