package com.example.surveyformapi;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SurveyFormServiceImpl implements SurveyFormService{

    SurveyFormRepository surveyFormRepository;

    public SurveyFormServiceImpl(SurveyFormRepository surveyFormRepository) {
        this.surveyFormRepository = surveyFormRepository;
    }

    @Override
    public List<SurveyForm> getSurveyForms() {
        return surveyFormRepository.findAll();
    }

    @Override
    public SurveyForm getSurveyForm(Long id) {
        Optional<SurveyForm> surveyForm = this.surveyFormRepository.findById(id);
        return surveyForm.orElse(null);
    }

    @Override
    public boolean addSurveyForm(SurveyForm surveyForm) {
        try{
            this.surveyFormRepository.save(surveyForm);
            return true;
        }catch(Exception e){
            return false;
        }
    }

    @Override
    public boolean deleteSurveyForm(Long id) {
        Optional<SurveyForm> surveyForm = this.surveyFormRepository.findById(id);
        if(surveyForm.isPresent()){
            this.surveyFormRepository.delete(surveyForm.get());
            return true;
        }else {
            return false;
        }
    }

    @Override
    public SurveyForm updateSurveyForm(Long id,SurveyForm surveyForm) {
        Optional<SurveyForm> surveyFormOptional = this.surveyFormRepository.findById(id);
        if(surveyFormOptional.isPresent()){
            SurveyForm surveyFormUpdated = surveyFormOptional.get();
            surveyFormUpdated.setFirstName(surveyForm.getFirstName());
            surveyFormUpdated.setLastName(surveyForm.getLastName());
            surveyFormUpdated.setEmail(surveyForm.getEmail());
            surveyFormUpdated.setPhone(surveyForm.getPhone());
            surveyFormUpdated.setAddress(surveyForm.getAddress());
            surveyFormUpdated.setCity(surveyForm.getCity());
            surveyFormUpdated.setState(surveyForm.getState());
            surveyFormUpdated.setZip(surveyForm.getZip());
            surveyFormUpdated.setDateOfBirth(surveyForm.getDateOfBirth());
            surveyFormUpdated.setAdditionalComments(surveyForm.getAdditionalComments());
            surveyFormUpdated.setQ2answer(surveyForm.getQ2answer());
            surveyFormUpdated.setQ3answer(surveyForm.getQ3answer());
            surveyFormUpdated.setQ11Option(surveyForm.getQ11Option());
            surveyFormUpdated.setQ12Option(surveyForm.getQ12Option());
            surveyFormUpdated.setQ13Option(surveyForm.getQ13Option());
            surveyFormUpdated.setQ14Option(surveyForm.getQ14Option());
            surveyFormUpdated.setQ15Option(surveyForm.getQ15Option());
            surveyFormUpdated.setRaffleNumbers(surveyForm.getRaffleNumbers());
            this.surveyFormRepository.save(surveyFormUpdated);
            return surveyFormUpdated;
        }else{
            return null;
        }
    }
}
