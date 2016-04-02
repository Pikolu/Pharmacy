package com.pharmacy.web.validator;

import com.pharmacy.web.dto.KeyAndPasswordDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * Pharmacy GmbH
 * Created by Alexander on 02.04.2016.
 */
@Component
public class PasswordValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return KeyAndPasswordDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        KeyAndPasswordDTO keyAndPasswordDTO = (KeyAndPasswordDTO) target;
        if (!keyAndPasswordDTO.getNewPassword().equals(keyAndPasswordDTO.getNewPasswordRepeat())) {
            errors.rejectValue("newPassword", "message.NotValidPassword");
        } else {
            validatePassword(keyAndPasswordDTO.getNewPassword(), errors);
        }
    }

    private void validatePassword(String password, Errors errors) {
        if (password.length() < 6) {
            errors.rejectValue("newPassword", "message.NotValidPassword");
        } else if (!password.matches(".*\\p{Lower}.*")) {
            errors.rejectValue("newPassword", "message.NotValidPassword");
        } else if (!password.matches(".*\\p{Upper}.*")) {
            errors.rejectValue("newPassword", "message.NotValidPassword");
        } else if (!password.matches(".*\\d.*")) {
            errors.rejectValue("newPassword", "message.NotValidPassword");
        }
    }
}
