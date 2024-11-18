package rk181.java_survey_app_backend.users;

import java.util.HashSet;
import java.util.Set;


import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import rk181.java_survey_app_backend.auth.dto.AuthDTO;
import rk181.java_survey_app_backend.survey_options.SurveyOption;

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
    Set<SurveyOption> surveyOption = new HashSet<>();


    public static User fromAuthDTO(AuthDTO authDTO) {
        return new User(authDTO.getNickname(), authDTO.getPassword());
    }
}
