package rk181.java_survey_app_backend.survey_options_users;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface SurveyOptionsUsersRepository extends JpaRepository<SurveyOptionsUsers, SurveyOptionsUsersPK> {

}
