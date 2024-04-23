package com.example.surveyformapi;


import java.util.List;

public interface SurveyFormService {

    List<SurveyForm> getSurveyForms();
    SurveyForm getSurveyForm(Long id);
    boolean addSurveyForm(SurveyForm surveyForm);
    boolean deleteSurveyForm(Long id);
    SurveyForm updateSurveyForm(Long id,SurveyForm surveyForm);
}
