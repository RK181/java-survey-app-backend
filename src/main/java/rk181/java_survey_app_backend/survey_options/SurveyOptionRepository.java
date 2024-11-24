package rk181.java_survey_app_backend.survey_options;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyOptionRepository extends JpaRepository<SurveyOption, Long> {
    Optional<SurveyOption> findBySurveyIdAndId(Long surveyId, Long id);
    boolean existsBySurveyIdAndId(Long surveyId, Long id);
}
