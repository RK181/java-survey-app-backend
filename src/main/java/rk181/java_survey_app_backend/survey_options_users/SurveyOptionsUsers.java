package rk181.java_survey_app_backend.survey_options_users;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@IdClass(SurveyOptionsUsersPK.class)
@Table(name = "survey_options_users")
public class SurveyOptionsUsers {
    @Id
    private Long user_id;
    @Id
    private Long survey_option_id;
    

    public SurveyOptionsUsers() {}

    public SurveyOptionsUsers(Long user_id, Long survey_option_id) {
        this.user_id = user_id;
        this.survey_option_id = survey_option_id;
    }

    public SurveyOptionsUsers(SurveyOptionsUsersPK id) {
        this.user_id = id.getUser_id();
        this.survey_option_id = id.getSurvey_option_id();
    }


}
