package com.example.surveyformapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class SurveyFormController {

    SurveyFormServiceImpl surveyFormService;
    public SurveyFormController(SurveyFormServiceImpl surveyFormService) {
        this.surveyFormService = surveyFormService;
    }

    @GetMapping
    public ResponseEntity<List<SurveyForm>> getSurveyForm() {
        System.out.println("Hello, world!");
        return new ResponseEntity<>(this.surveyFormService.getSurveyForms(), HttpStatus.OK);
    }

    @GetMapping("/{surveyId}")
    public ResponseEntity<SurveyForm> getSurveyFormById(@PathVariable Long surveyId) {
        SurveyForm surveyForm = this.surveyFormService.getSurveyForm(surveyId);
        if(surveyForm != null) {
            return new ResponseEntity<>(surveyForm, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<SurveyForm> createSurveyForm(@RequestBody SurveyForm surveyForm) {
        if(this.surveyFormService.addSurveyForm(surveyForm)) {
            return new ResponseEntity<>(surveyForm, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<SurveyForm> updateSurveyForm(@PathVariable Long id,@RequestBody SurveyForm surveyForm) {
        SurveyForm surveyform = this.surveyFormService.updateSurveyForm(id,surveyForm);
        if(surveyform != null) {
            return new ResponseEntity<>(surveyform, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    @DeleteMapping("/{surveyId}")
    public ResponseEntity<SurveyForm> deleteSurveyForm(@PathVariable Long surveyId) {
        SurveyForm surveyForm = this.surveyFormService.getSurveyForm(surveyId);
        if(this.surveyFormService.deleteSurveyForm(surveyId)) {

            return new ResponseEntity<>(surveyForm, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
