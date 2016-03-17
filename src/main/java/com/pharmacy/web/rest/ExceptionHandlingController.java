package com.pharmacy.web.rest;

import com.pharmacy.exceptions.ControllerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;
import java.util.Date;

/**
 * Pharmacy GmbH
 * Created by Alexander on 17.03.2016.
 */
@ControllerAdvice
public class ExceptionHandlingController {

    public static final String DEFAULT_ERROR_VIEW = "error";

    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception e) {
        ModelAndView mav = new ModelAndView(DEFAULT_ERROR_VIEW);

        mav.addObject("datetime", new Date());
        mav.addObject("exception", e);
        mav.addObject("url", request.getRequestURL());
        return mav;
    }
}
