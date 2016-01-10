package com.pharmacy.security;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by Alexander on 28.12.2015.
 */
public class UserNotActivatedException extends AuthenticationException {

    public UserNotActivatedException(String message) {
        super(message);
    }

    public UserNotActivatedException(String message, Throwable t) {
        super(message, t);
    }
}
