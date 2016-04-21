package com.pharmacy.web.rest;

import com.pharmacy.domain.Evaluation;
import com.pharmacy.domain.Pharmacy;
import com.pharmacy.domain.SearchResult;
import com.pharmacy.service.api.EvaluationService;
import com.pharmacy.service.api.PharmacyService;
import org.apache.commons.math3.analysis.function.Abs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.Collections;

/**
 * Pharmacy GmbH
 * Created by Alexander on 01.03.2016.
 */
@Controller
public class PharmacyController extends AbstractController {

    private final static Logger LOG = LoggerFactory.getLogger(PharmacyController.class);

    private static final int DEFAULT_SIZE = 5;

    @Inject
    private PharmacyService pharmacyService;
    @Inject
    private EvaluationService evaluationService;

    @RequestMapping(value = "/apotheke/{id}/{pharm}", method = RequestMethod.GET)
    public ModelAndView displayPharmacy(@PathVariable String id, @PathVariable String pharm) {
        ModelAndView modelAndView = new ModelAndView("pharmacy");
        Pharmacy pharmacy = pharmacyService.getPharmacyById(id);
        modelAndView.addObject("pharmacy", pharmacy);
        modelAndView.addObject("searchResult", new SearchResult());
        modelAndView.addObject("evaluations", evaluationService.getLastEvaluations(DEFAULT_SIZE));

        return modelAndView;
    }
}
