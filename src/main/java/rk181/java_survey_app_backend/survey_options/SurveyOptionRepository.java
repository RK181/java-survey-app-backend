package rk181.java_survey_app_backend.survey_options;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyOptionRepository extends JpaRepository<SurveyOption, Long> {

}
