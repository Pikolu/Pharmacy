package com.pharmacy.web.rest;

import com.pharmacy.config.Constants;
import com.pharmacy.domain.SearchResult;
import com.pharmacy.domain.pojo.ContactForm;
import com.pharmacy.service.api.MailService;
import com.pharmacy.service.impl.MailServiceImpl;
import com.pharmacy.web.validator.ContactValidator;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 * Pharmacy GmbH
 * Created by Alexander on 10.03.2016.
 */
@Controller
public class ContactController extends AbstractController {

    @Inject
    private ContactValidator contactValidator;

    @Inject
    private MailServiceImpl mailService;

    @RequestMapping(value = "/kontakt", method = RequestMethod.GET)
    public ModelAndView initContactForm(ModelAndView model) {
        model.setViewName("contact");
        model.addObject("contactForm", new ContactForm());
        model.addObject("searchResult", new SearchResult());
        return model;
    }

    @RequestMapping(value = "/index", method = RequestMethod.POST)
    public ModelAndView validateAndSendEmail(@ModelAttribute("contactForm") ContactForm contactForm, BindingResult result, HttpServletRequest request) {
        contactValidator.validate(contactForm, result);
        ModelAndView modelAndView;
        if (result.hasErrors()) {
            if (contactForm.isError()) {
                modelAndView = new ModelAndView("error");
            } else {
                modelAndView = new ModelAndView("contact");
            }
            modelAndView.addObject("contactForm", contactForm);
            super.fillModel(modelAndView);
        } else {
            modelAndView = new ModelAndView("index");
            modelAndView.addObject("contactSuccessful", true);
            mailService.sendContactEmail(contactForm, Constants.BASE_URL);
            super.fillIndexModel(modelAndView);
        }
        return modelAndView;
    }


}
