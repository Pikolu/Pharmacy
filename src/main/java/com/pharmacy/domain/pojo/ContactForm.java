package com.pharmacy.domain.pojo;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Max;

/**
 * Pharmacy GmbH
 * Created by Alexander on 10.03.2016.
 */
public class ContactForm {

    @Max(255)
    private String subject;
    @Max(4000)
    private String message;
    private String fistName;
    private String lastName;
    @Email
    private String email;
    private boolean error;

    public ContactForm() {

    }

    public ContactForm(boolean error) {
        this.error = error;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFistName() {
        return fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
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

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }
}
