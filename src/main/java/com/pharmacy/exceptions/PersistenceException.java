package com.pharmacy.exceptions;

import com.pharmacy.exceptions.type.ExceptionType;

import java.util.List;

/**
 * Created by Alexander on 28.12.2015.
 */
public class PersistenceException extends ServiceException {

    public PersistenceException(ExceptionType exceptionType) {
        super(exceptionType);
    }

    public PersistenceException(ExceptionType exceptionType, List<ExceptionType> exceptionTypes) {
        super(exceptionType, exceptionTypes);
    }

    public PersistenceException(ExceptionType exceptionType, String... details) {
        super(exceptionType, details);
    }

    public PersistenceException(ExceptionType exceptionType, Throwable throwable) {
        super(exceptionType, throwable);
    }

    public PersistenceException(ExceptionType exceptionType, Throwable throwable, List<ExceptionType> exceptionTypes) {
        super(exceptionType, throwable, exceptionTypes);
    }

    public PersistenceException(ExceptionType exceptionType, Throwable throwable, String... details) {
        super(exceptionType, throwable, details);
    }
}
