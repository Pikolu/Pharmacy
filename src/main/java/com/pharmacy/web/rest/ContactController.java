package com.pharmacy.web.rest;

import com.pharmacy.domain.pojo.ContactForm;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
/**
 * Pharmacy GmbH
 * Created by Alexander on 10.03.2016.
 */
@Controller
public class ContactController extends AbstractController {

    @RequestMapping(value = "/kontakt")
    public ModelAndView initContactForm(ModelAndView model) {
        model.setViewName("contact");
        model.addObject("contactForm", new ContactForm());
        return model;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public ModelAndView registration(@ModelAttribute("contactForm") ContactForm user, BindingResult result) {
        ModelAndView modelAndView = new ModelAndView("index");
        return modelAndView;
    }


}
