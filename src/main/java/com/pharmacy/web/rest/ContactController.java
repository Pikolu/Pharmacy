package com.pharmacy.web.rest;

import com.pharmacy.domain.SearchResult;
import com.pharmacy.domain.pojo.ContactForm;
import com.pharmacy.service.api.MailService;
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
    private MailService mailService;

    @RequestMapping(value = "/kontakt", method = RequestMethod.GET)
    public ModelAndView initContactForm(ModelAndView model) {
        model.setViewName("contact");
        model.addObject("contactForm", new ContactForm());
        model.addObject("searchResult", new SearchResult());
        return model;
    }

    @RequestMapping(value = "/kontakt", method = RequestMethod.POST)
    public ModelAndView validateAndSendEmail(@ModelAttribute("contactForm") ContactForm contactForm, BindingResult result, HttpServletRequest request) {
        contactValidator.validate(contactForm, result);
        ModelAndView modelAndView;
        if (result.hasErrors()) {
            modelAndView = new ModelAndView("contact");
            modelAndView.addObject("contactForm", contactForm);
        } else {
            modelAndView = new ModelAndView("index");
            String baseUrl = request.getScheme() + // "http"
                    "://" +                                // "://"
                    request.getServerName() +              // "myhost"
                    ":" +                                  // ":"
                    request.getServerPort();               // "80"
            mailService.sendContactEmail(contactForm, baseUrl);
        }
        modelAndView.addObject("searchResult", new SearchResult());
        return modelAndView;
    }


}
