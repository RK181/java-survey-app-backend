package rk181.java_survey_app_backend.surveys;

import java.util.HashSet;
import java.util.Set;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import rk181.java_survey_app_backend.survey_options.SurveyOption;
import rk181.java_survey_app_backend.users.User;

@Entity
@Table(name = "surveys")
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private Integer total_votes; 

    public Survey() {}

    public Survey(String title, String description, Integer total_votes) {
        this.title = title;
        this.description = description;
        this.total_votes = total_votes;
    }

    @NotNull
    // ManyToOne relationship with User
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // OneToMany relationship with SurveyOption
    // EAGER fetch type is used to load all the survey options when a survey is loaded
    @OneToMany(mappedBy = "survey", fetch = FetchType.EAGER)
    Set<SurveyOption> surveyOptions = new HashSet<>();
}
