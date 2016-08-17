package com.pharmacy.web.rest;

import com.pharmacy.domain.Article;
import com.pharmacy.domain.Evaluation;
import com.pharmacy.domain.Pharmacy;
import com.pharmacy.domain.SearchResult;
import com.pharmacy.service.api.ArticleService;
import com.pharmacy.service.api.EvaluationService;
import com.pharmacy.service.api.PharmacyService;
import org.apache.commons.math3.analysis.function.Abs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

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
    @Inject
    private ArticleService articleService;

    @RequestMapping(value = "/apotheke/{id}/{pharm}", method = RequestMethod.GET)
    public ModelAndView displayPharmacy(@PathVariable String id, @PathVariable String pharm, Pageable pageable, @ModelAttribute("searchResult") SearchResult searchResult) {
        ModelAndView modelAndView = new ModelAndView("pharmacy");
        Pharmacy pharmacy = pharmacyService.getPharmacyById(id);
        modelAndView.addObject("pharmacy", pharmacy);
        modelAndView.addObject("evaluations", evaluationService.getLastEvaluations(DEFAULT_SIZE));

        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        if (pageNumber > 0) {
            pageNumber = pageNumber - 1;
        }
        Pageable newPageable = new PageRequest(pageNumber, pageSize);
        Page<Article> products = articleService.findProducsForPharmacy(newPageable, pharmacy);
        searchResult.setSearchPage(products);
        searchResult.buildPagination(pageable.getPageNumber(), modelAndView, products.getTotalElements());
        modelAndView.addObject("products", products.getContent());

        fillModel(modelAndView);
        modelAndView.addObject("searchResult", searchResult);

        return modelAndView;
    }
}
