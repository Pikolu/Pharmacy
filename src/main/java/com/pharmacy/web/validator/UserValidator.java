package com.pharmacy.web.validator;

import com.pharmacy.domain.User;
import com.pharmacy.service.api.UserService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.inject.Inject;

/**
 * Created by Alexander on 28.12.2015.
 */
@Component
public class UserValidator implements Validator {

    private static final Logger LOG = LoggerFactory.getLogger(UserValidator.class);

    @Inject
    private UserService userService;

    private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-+]+(.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(.[A-Za-z0-9]+)*(.[A-Za-z]{2,})$";


    @Override
    public void validate(Object target, Errors errors) {
        LOG.trace("Enter validate: target={}, errors={}", target, errors);
        User user = (User) target;

        if (StringUtils.isBlank(user.getLogin())) {
            errors.rejectValue("login", "message.EmptyLogin");
        }
        if (user.getFirstName() == null || user.getFirstName().isEmpty()) {
            errors.rejectValue("firstName", "message.EmptyFirstname");
        }
        if (user.getLastName() == null || user.getLastName().isEmpty()) {
            errors.rejectValue("lastName", "message.EmptyLastname");
        }
        if (user.getLastName() == null || user.getLastName().isEmpty()) {
            errors.rejectValue("password", "message.EmptyPassword");
        }
        if (StringUtils.isBlank(user.getEmail())) {
            errors.rejectValue("email", "message.EmptyEmail");
        } else {
            EmailValidator emailValidator = EmailValidator.getInstance();
            if (!emailValidator.isValid(user.getEmail())) {
                errors.rejectValue("email", "message.NotValidEmail");
            }
        }

        validatePasswort(user.getPassword(), errors);

        LOG.debug("exit");
    }

    private void validatePasswort(String password, Errors errors) {
        if (password.length() < 6) {
            errors.rejectValue("password", "message.NotValidLength");
        } else if (!password.matches("[a-z]")) {
            errors.rejectValue("password", "message.NotValidLetters");
        } else if (!password.matches("[A-Z]")) {
            errors.rejectValue("password", "message.NotValidCapitalLetters");
        } else if (!password.matches(".*\\\\d.*")) {
            errors.rejectValue("password", "message.NotValidNumbers");
        }
    }

    @Override
    public boolean supports(Class type) {
        return User.class.equals(type);
    }
}
