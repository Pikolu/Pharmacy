package com.pharmacy.web.rest;

import com.pharmacy.config.SecurityUtils;
import com.pharmacy.domain.Article;
import com.pharmacy.domain.Evaluation;
import com.pharmacy.domain.Pharmacy;
import com.pharmacy.domain.SearchResult;
import com.pharmacy.security.CustomUserDetails;
import com.pharmacy.service.api.ArticleService;
import com.pharmacy.service.api.EvaluationService;
import com.pharmacy.service.api.ImportService;
import com.pharmacy.service.api.PharmacyService;
import com.pharmacy.web.helper.ArticleHelper;
import com.pharmacy.web.helper.EvaluationHelper;
import com.pharmacy.web.helper.URLHelper;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Alexander on 02.01.2016.
 */
public class AbstractController {

    private static final int DEFAULT_SIZE = 5;

    @Inject
    private ArticleService articleService;
    @Inject
    private PharmacyService pharmacyService;
    @Inject
    private EvaluationService evaluationService;

    @ModelAttribute("customUser")
    public CustomUserDetails getCustomUserDetails() {
        return SecurityUtils.getCurrentUser();
    }

    public void fillModel(ModelAndView modelAndView) {
        modelAndView.addObject("searchResult", new SearchResult());
        modelAndView.addObject("articleHelper", new ArticleHelper());
        modelAndView.addObject("urlEncoder", new URLHelper());
        modelAndView.addObject("evaluationHelper", new EvaluationHelper());
    }

    public void fillIndexModel(ModelAndView modelAndView) {
        List<Article> articles = articleService.loadBestDiscountedArticles();
        List<Pharmacy> pharmacies = pharmacyService.findBestPharmacies();
        List<Evaluation> evaluations = evaluationService.getLastEvaluations(DEFAULT_SIZE);
        modelAndView.addObject("articles", articles);
        modelAndView.addObject("pharmacies", pharmacies);
        modelAndView.addObject("evaluations", evaluations);
        this.fillModel(modelAndView);
    }
}
