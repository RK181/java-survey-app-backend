package rk181.java_survey_app_backend.survey_options;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.Formula;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import rk181.java_survey_app_backend.survey_options.dto.SurveyOptionDTO;
import rk181.java_survey_app_backend.surveys.Survey;
import rk181.java_survey_app_backend.users.User;

@Data
@Entity
@Table(name = "survey_options")
public class SurveyOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    
    @Formula("(select count(so1_0.user_id) from survey_options_users so1_0 where so1_0.survey_option_id = id)")
    private Integer total_votes = 0;

    public SurveyOption() {}

    public SurveyOption(String name, Survey survey) {
        this.name = name;
        this.survey = survey;
    }

    @NotNull
    // ManyToOne relationship with Survey
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "survey_id")
    private Survey survey;

    // ManyToMany relationship with User, this is the owning side
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "survey_options_users",
            joinColumns = {@JoinColumn(name = "survey_option_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    List<User> users = new ArrayList<>();

    public static SurveyOption fromDTO(SurveyOptionDTO surveyOptionDTO, Survey survey) {
        return new SurveyOption(surveyOptionDTO.getName(), survey);
    }
}
