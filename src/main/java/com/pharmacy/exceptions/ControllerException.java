package com.pharmacy.exceptions;

import com.pharmacy.exceptions.type.ExceptionType;
import com.pharmacy.exceptions.type.PharmacyException;

import java.util.List;

/**
 * Created by apopow on 25.12.2015.
 */
public class ControllerException extends PharmacyException {

    public ControllerException(ExceptionType exceptionType) {
        super(exceptionType);
    }

    public ControllerException(ExceptionType exceptionType, List<ExceptionType> exceptionTypes) {
        super(exceptionType, exceptionTypes);
    }

    public ControllerException(ExceptionType exceptionType, String... details) {
        super(exceptionType, details);
    }

    public ControllerException(ExceptionType exceptionType, Throwable throwable) {
        super(exceptionType, throwable);
    }

    public ControllerException(ExceptionType exceptionType, Throwable throwable, List<ExceptionType> exceptionTypes) {
        super(exceptionType, throwable, exceptionTypes);
    }

    public ControllerException(ExceptionType exceptionType, Throwable throwable, String... details) {
        super(exceptionType, throwable, details);
    }
}
