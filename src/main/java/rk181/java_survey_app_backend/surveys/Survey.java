package rk181.java_survey_app_backend.surveys;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.Formula;

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
import lombok.Data;
import rk181.java_survey_app_backend.survey_options.SurveyOption;
import rk181.java_survey_app_backend.surveys.dto.SurveyDTO;
import rk181.java_survey_app_backend.users.User;

@Data
@Entity
@Table(name = "surveys")
public class Survey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @Formula("(select count(so1_0.user_id) from survey_options_users so1_0 where so1_0.survey_option_id in (select so1_0.id from survey_options so1_0 where so1_0.survey_id = id))")
    private Integer total_votes = 0; 

    public Survey() {}

    public Survey(User user, String title, String description) {
        this.title = title;
        this.description = description;
        this.user = user;
    }

    @NotNull
    // ManyToOne relationship with User
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    // OneToMany relationship with SurveyOption
    // EAGER fetch type is used to load all the survey options when a survey is loaded
    @OneToMany(mappedBy = "survey", fetch = FetchType.LAZY)
    List<SurveyOption> surveyOptions = new ArrayList<>();

    public static Survey fromDTO (User authUser, SurveyDTO surveyDTO) {
        return new Survey(authUser, surveyDTO.getTitle(), surveyDTO.getDescription());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Survey survey = (Survey) o;
        if (id != null && survey.id != null)
            // Si tenemos los ID, comparamos por ID
            return Objects.equals(id, survey.id);
        return false;
    }

    @Override
    public int hashCode() {
        // Generamos un hash basado en los campos obligatorios
        return Objects.hash(id);
    }
}
