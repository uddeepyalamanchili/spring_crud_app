package com.example.surveyformapi;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

enum q2Option{
    FRIEND, TELEVISION, INTERNET, OTHER
}

enum q3Option{
    VERY_LIKELY,
    LIKELY,
    UNLIKELY
}

@Entity
public class SurveyForm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String zip;
    //new
//    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;
    private boolean q11Option;
    private boolean q12Option;
    private boolean q13Option;
    private boolean q14Option;
    private boolean q15Option;

    @Enumerated(EnumType.STRING)
    private q2Option q2answer;
    @Enumerated(EnumType.STRING)
    private q3Option q3answer;

    private String raffleNumbers;
    private String additionalComments;
    public SurveyForm() {
    }

    public SurveyForm(Long id, String firstName, String lastName, String email, String phone, String address, String city, String state, String zip, LocalDate dateOfBirth, boolean q11Option, boolean q12Option, boolean q13Option, boolean q14Option, boolean q15Option, q2Option q2answer, q3Option q3answer, String raffleNumbers, String additionalComments) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.dateOfBirth = dateOfBirth;
        this.q11Option = q11Option;
        this.q12Option = q12Option;
        this.q13Option = q13Option;
        this.q14Option = q14Option;
        this.q15Option = q15Option;
        this.q2answer = q2answer;
        this.q3answer = q3answer;
        this.raffleNumbers = raffleNumbers;
        this.additionalComments = additionalComments;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public boolean getQ11Option() {
        return q11Option;
    }

    public void setQ11Option(boolean q11Option) {
        this.q11Option = q11Option;
    }

    public boolean getQ12Option() {
        return q12Option;
    }

    public void setQ12Option(boolean q12Option) {
        this.q12Option = q12Option;
    }

    public boolean getQ13Option() {
        return q13Option;
    }

    public void setQ13Option(boolean q13Option) {
        this.q13Option = q13Option;
    }

    public boolean getQ14Option() {
        return q14Option;
    }

    public void setQ14Option(boolean q14Option) {
        this.q14Option = q14Option;
    }

    public boolean getQ15Option() {
        return q15Option;
    }

    public void setQ15Option(boolean q15Option) {
        this.q15Option = q15Option;
    }

    public q2Option getQ2answer() {
        return q2answer;
    }

    public void setQ2answer(q2Option q2answer) {
        this.q2answer = q2answer;
    }

    public q3Option getQ3answer() {
        return q3answer;
    }

    public void setQ3answer(q3Option q3answer) {
        this.q3answer = q3answer;
    }

    public String getRaffleNumbers() {
        return raffleNumbers;
    }

    public void setRaffleNumbers(String raffleNumbers) {
        this.raffleNumbers = raffleNumbers;
    }

    public String getAdditionalComments() {
        return additionalComments;
    }

    public void setAdditionalComments(String additionalComments) {
        this.additionalComments = additionalComments;
    }
}
