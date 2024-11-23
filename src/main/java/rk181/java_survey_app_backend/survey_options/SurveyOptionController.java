package rk181.java_survey_app_backend.survey_options;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import rk181.java_survey_app_backend.survey_options.dto.SurveyOptionDTO;

import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/surveys/{surveyId}/options")
public class SurveyOptionController {

    private final SurveyOptionService surveyOptionService;

    public SurveyOptionController(SurveyOptionService surveyOptionService) {
        this.surveyOptionService = surveyOptionService;
    }

    @PostMapping()
    public SurveyOptionDTO creaSurveyOption(@PathVariable Long surveyId, @RequestBody SurveyOptionDTO surveyOptionDTO) {
        return surveyOptionService.createSurveyOption(surveyId, surveyOptionDTO);
    }

    @PatchMapping("/{surveyOptionId}")
    public SurveyOptionDTO postMethodName(@PathVariable Long surveyId, @PathVariable Long surveyOptionId) {
        return surveyOptionService.vote(surveyId, surveyOptionId);
    }
    

}
