package rk181.java_survey_app_backend.survey_options;

import java.util.HashSet;
import java.util.Set;


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
import rk181.java_survey_app_backend.surveys.Survey;
import rk181.java_survey_app_backend.users.User;

@Entity
@Table(name = "survey_options")
public class SurveyOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer total_votes;

    public SurveyOption() {}

    public SurveyOption(String name, Integer total_votes) {
        this.name = name;
        this.total_votes = total_votes;
    }

    @NotNull
    // ManyToOne relationship with Survey
    @ManyToOne 
    @JoinColumn(name = "survey_id")
    private Survey survey;

    // ManyToMany relationship with User, this is the owning side
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "survey_options_users",
            joinColumns = {@JoinColumn(name = "survey_option_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    Set<User> users = new HashSet<>();
}
