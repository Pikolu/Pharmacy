package com.pharmacy.web.rest;

import com.pharmacy.domain.SearchResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Pharmacy GmbH
 * Created by Alexander on 02.04.2016.
 */
@Controller
public class PrivacyController {

    @RequestMapping(value = "/datenschutz", method = RequestMethod.GET)
    public ModelAndView showPrivacy() {
        ModelAndView modelAndView = new ModelAndView("privacy");
        modelAndView.addObject("searchResult", new SearchResult());
        return modelAndView;
    }
}
