package com.pharmacy.web.rest;

import com.pharmacy.domain.Evaluation;
import com.pharmacy.domain.Pharmacy;
import com.pharmacy.domain.SearchResult;
import com.pharmacy.service.api.EvaluationService;
import com.pharmacy.service.api.PharmacyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 * Created by Alexander on 09.01.2016.
 */
@Controller
public class EvaluationController extends AbstractController {

    private final static Logger LOG = LoggerFactory.getLogger(EvaluationController.class);

    @Inject
    private PharmacyService pharmacyService;
    @Inject
    private EvaluationService evaluationService;

    @RequestMapping(value = "/bewertungen", method = RequestMethod.GET)
    public ModelAndView initEvaluations() {
        ModelAndView model = new ModelAndView();
        // check if user is login
        if (getCustomUserDetails() != null) {
            model.setViewName("evaluations");
            model.addObject("pharmacies", new PageImpl<Pharmacy>(new ArrayList<>()));
        } else {
            model.setViewName("login");
        }
        model.addObject("searchResult", new SearchResult());
        return model;
    }

    @RequestMapping(value = "/apotheken", method = RequestMethod.GET)
    public ModelAndView findPharmacy(@RequestParam String pharm, Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("evaluations");
        final Page<Pharmacy> pharmacies = pharmacyService.getPharmacyByName(pharm, pageable);
        modelAndView.addObject("evaluations", new Evaluation());
        modelAndView.addObject("pharm", pharm);
        modelAndView.addObject("pharmacies", pharmacies);
        modelAndView.addObject("searchResult", new SearchResult());
        return modelAndView;
    }

    @RequestMapping(value = "/bewerten/{pharmId}/{name}", method = RequestMethod.GET)
    public ModelAndView evaluate(@PathVariable String pharmId, @PathVariable String name) {
        ModelAndView modelAndView = null;
        modelAndView = new ModelAndView("evaluate", "evaluation", new Evaluation());
        Pharmacy pharmacy = pharmacyService.getPharmacyById(pharmId);
        modelAndView.addObject("pharmacy", pharmacy);
        modelAndView.addObject("searchResult", new SearchResult());
        return modelAndView;
    }

    @RequestMapping(value = "/bewerten/{pharmId}/{name}", method = RequestMethod.POST)
    public ModelAndView evaluate(@ModelAttribute("evaluation") Evaluation evaluation, BindingResult result, @PathVariable String pharmId, @PathVariable String name) {
        ModelAndView modelAndView = null;
        modelAndView = new ModelAndView("evaluate", "evaluation", new Evaluation());
        Pharmacy pharmacy = pharmacyService.getPharmacyById(pharmId);
        evaluationService.evaluate(evaluation, pharmacy);
        modelAndView.addObject("pharmacy", pharmacy);
        modelAndView.addObject("searchResult", new SearchResult());
        evaluationService.save(evaluation);
        return modelAndView;
    }
}
