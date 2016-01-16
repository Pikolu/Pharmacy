package com.pharmacy.exceptions;

import com.pharmacy.exceptions.type.ExceptionType;

import java.util.List;

/**
 * Created by Alexander on 28.12.2015.
 */
public class ServiceException extends ControllerException {

    public ServiceException(ExceptionType exceptionType) {
        super(exceptionType);
    }

    public ServiceException(ExceptionType exceptionType, List<ExceptionType> exceptionTypes) {
        super(exceptionType, exceptionTypes);
    }

    public ServiceException(ExceptionType exceptionType, String... details) {
        super(exceptionType, details);
    }

    public ServiceException(ExceptionType exceptionType, Throwable throwable) {
        super(exceptionType, throwable);
    }

    public ServiceException(ExceptionType exceptionType, Throwable throwable, List<ExceptionType> exceptionTypes) {
        super(exceptionType, throwable, exceptionTypes);
    }

    public ServiceException(ExceptionType exceptionType, Throwable throwable, String... details) {
        super(exceptionType, throwable, details);
    }
}
