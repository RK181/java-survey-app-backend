package rk181.java_survey_app_backend.users;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.*;
import lombok.Data;
import rk181.java_survey_app_backend.auth.dto.AuthDTO;
import rk181.java_survey_app_backend.survey_options.SurveyOption;
import rk181.java_survey_app_backend.surveys.Survey;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nickname;
    private String password;
    private String token;



    public User() {}

    public User(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
        this.token = null;
    }

    // ManyToMany relationship with SurveyOption
    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    List<SurveyOption> surveyOption = new ArrayList<>();

    // OneToMany relationship with Surveys
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    List<Survey> surveys = new ArrayList<>();

    public static User fromAuthDTO(AuthDTO authDTO) {
        return new User(authDTO.getNickname(), authDTO.getPassword());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        if (id != null && user.id != null)
            // Si tenemos los ID, comparamos por ID
            return Objects.equals(id, user.id);
        // sino comparamos por campos obligatorios
        return nickname.equals(user.nickname);
    }

    @Override
    public int hashCode() {
        // Generamos un hash basado en los campos obligatorios
        return Objects.hash(nickname);
    }
}
