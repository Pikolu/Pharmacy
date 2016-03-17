package com.pharmacy.web.rest;

import com.pharmacy.domain.SearchResult;
import com.pharmacy.domain.pojo.ContactForm;
import com.pharmacy.service.api.UserService;
import com.pharmacy.service.impl.MailServiceImpl;
import com.pharmacy.web.validator.ContactValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Pharmacy GmbH
 * Created by Alexander on 17.03.2016.
 */
@ControllerAdvice
public class ExceptionHandlingController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(ExceptionHandlingController.class);

    @Inject
    private ContactValidator contactValidator;
    @Inject
    private MailServiceImpl mailService;

    public static final String DEFAULT_ERROR_VIEW = "error";

    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    public ModelAndView defaultErrorHandler(HttpServletRequest request, Exception e) {
        LOG.error("Exception: {},", e);
        ModelAndView mav = new ModelAndView(DEFAULT_ERROR_VIEW);
        mav.addObject("datetime", new Date());
        mav.addObject("exception", e);
        mav.addObject("url", request.getRequestURL());
        mav.addObject("searchResult", new SearchResult());
        mav.addObject("contactForm", new ContactForm(true));
        return mav;
    }
}
