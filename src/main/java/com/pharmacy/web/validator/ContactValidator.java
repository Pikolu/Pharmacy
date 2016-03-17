package com.pharmacy.web.validator;

import com.pharmacy.domain.pojo.ContactForm;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Pharmacy GmbH
 * Created by Alexander on 11.03.2016.
 */
@Component
public class ContactValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return ContactForm.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {

        ContactForm contactForm = (ContactForm) target;
        if (StringUtils.isBlank(contactForm.getFistName())) {
            errors.rejectValue("firstName", "message.EmptyFirstname");
        }
        if (StringUtils.isBlank(contactForm.getLastName())) {
            errors.rejectValue("lastName", "message.EmptyLastname");
        }
        if (StringUtils.isBlank(contactForm.getEmail())) {
            errors.rejectValue("email", "message.EmptyEmail");
        }
        if (StringUtils.isBlank(contactForm.getSubject())) {
            errors.rejectValue("lastName", "message.EmptySubject");
        }
        if (StringUtils.isBlank(contactForm.getMessage())) {
            errors.rejectValue("lastName", "message.EmptyMessage");
        }
    }
}
