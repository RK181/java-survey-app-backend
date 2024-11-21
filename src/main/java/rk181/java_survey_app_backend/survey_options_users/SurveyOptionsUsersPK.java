package rk181.java_survey_app_backend.survey_options_users;

import java.io.Serializable;
import java.util.Objects;


public class SurveyOptionsUsersPK implements Serializable{
    private Long user_id;
    private Long survey_option_id;

    public SurveyOptionsUsersPK() {}

    public SurveyOptionsUsersPK(Long user_id, Long surveyOption_id) {
        this.user_id = user_id;
        this.survey_option_id = surveyOption_id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public Long getSurvey_option_id() {
        return survey_option_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) 
            return true;
        if (!(o instanceof SurveyOptionsUsersPK surveyOptionsUsers_PK)) 
            return false;
        
        return user_id == surveyOptionsUsers_PK.user_id && survey_option_id == surveyOptionsUsers_PK.survey_option_id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(user_id+survey_option_id);
    }
}
