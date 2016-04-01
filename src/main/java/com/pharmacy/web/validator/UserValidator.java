package com.pharmacy.web.validator;

import com.pharmacy.domain.User;
import com.pharmacy.service.api.UserService;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.inject.Inject;
import java.util.Optional;

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
        } else {
            userService.findOneByLogin(user.getLogin()).ifPresent(u -> {
                errors.rejectValue("login", "message.AlreadyInUseLogin");
            });
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
            } else {
                userService.findOneByEmail(user.getEmail()).ifPresent(u -> {
                    errors.rejectValue("email", "message.AlreadyInUseEmail");
                });
            }
        }
        if (BooleanUtils.isFalse(user.getAcceptedPrivacy())) {
            errors.rejectValue("acceptedPrivacy", "message.PrivacyNotAccepted");
        }

        validatePassword(user.getPassword(), errors);

        LOG.debug("exit");
    }

    private void validatePassword(String password, Errors errors) {
        if (password.length() < 6) {
            errors.rejectValue("password", "message.NotValidPassword");
        } else if (!password.matches(".*\\p{Lower}.*")) {
            errors.rejectValue("password", "message.NotValidPassword");
        } else if (!password.matches(".*\\p{Upper}.*")) {
            errors.rejectValue("password", "message.NotValidPassword");
        } else if (!password.matches(".*\\d.*")) {
            errors.rejectValue("password", "message.NotValidPassword");
        }
    }

    @Override
    public boolean supports(Class type) {
        return User.class.equals(type);
    }
}
