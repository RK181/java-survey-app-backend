package rk181.java_survey_app_backend.surveys;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import rk181.java_survey_app_backend.surveys.dto.SurveyDTO;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/surveys")
public class SurveyController {

    private final SurveyService surveyService;

    public SurveyController(SurveyService surveyService) {
        this.surveyService = surveyService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public SurveyDTO createSurvey(@Valid @RequestBody SurveyDTO surveyDTO) {
        return surveyService.createSurvey(surveyDTO);
    }

    @GetMapping()
    public List<SurveyDTO> getAllSurveys(@RequestParam(required = false) Integer offset, 
                                         @RequestParam(required = false) Integer limit) 
    {
        return surveyService.getAllSurveys(offset, limit);
    }

    @GetMapping("/{surveyId}")
    public SurveyDTO getSurveyByID(@PathVariable Long surveyId) {
        return surveyService.getSurveyById(surveyId);
    }

    @PutMapping("/{surveyId}")
    @ResponseStatus(HttpStatus.OK)
    public SurveyDTO putSurveyByID(@PathVariable Long surveyId, @Valid @RequestBody SurveyDTO surveyDTO) {
        return surveyService.putSurveyById(surveyId, surveyDTO);
    }

    @DeleteMapping("/{surveyId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSurveyByID(@PathVariable Long surveyId) {
        surveyService.deleteSurveyById(surveyId);
    }
}
