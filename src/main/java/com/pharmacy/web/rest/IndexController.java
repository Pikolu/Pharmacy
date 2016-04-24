package com.pharmacy.web.rest;

import com.pharmacy.domain.Article;
import com.pharmacy.domain.Evaluation;
import com.pharmacy.domain.Pharmacy;
import com.pharmacy.domain.SearchResult;
import com.pharmacy.domain.pojo.ContactForm;
import com.pharmacy.service.api.ArticleService;
import com.pharmacy.service.api.EvaluationService;
import com.pharmacy.service.api.ImportService;
import com.pharmacy.service.api.PharmacyService;
import com.pharmacy.web.helper.ArticleHelper;
import com.pharmacy.web.helper.EvaluationHelper;
import com.pharmacy.web.helper.URLHelper;
import com.redfin.sitemapgenerator.ChangeFreq;
import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.WebSitemapUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Alexander on 09.11.2015.
 */
@Controller
public class IndexController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(IndexController.class);

    private static final int DEFAULT_SIZE = 5;

    @Inject
    private ImportService importService;
    @Inject
    private ArticleService articleService;
    @Inject
    private PharmacyService pharmacyService;
    @Inject
    private EvaluationService evaluationService;

    @RequestMapping(value = {"/", "/index", "/welcome**"}, method = RequestMethod.GET)
    public ModelAndView index(HttpServletRequest request) {

        LOG.info("Index/Welcome page called from {}", getCustomUserDetails());
        Map<String, String[]> parameterMap = request.getParameterMap();
        List<Article> articles = articleService.loadBestDiscountedArticles();
        List<Pharmacy> pharmacies = pharmacyService.findBestPharmacies();
        List<Evaluation> evaluations = evaluationService.getLastEvaluations(DEFAULT_SIZE);
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addAllObjects(parameterMap);
        modelAndView.addObject("searchResult", new SearchResult());
        modelAndView.addObject("articles", articles);
        modelAndView.addObject("pharmacies", pharmacies);
        modelAndView.addObject("evaluations", evaluations);
        modelAndView.addObject("articleHelper", new ArticleHelper());
        modelAndView.addObject("urlEncoder", new URLHelper());
        modelAndView.addObject("evaluationHelper", new EvaluationHelper());
        return modelAndView;
    }



    @RequestMapping(value = "/error", produces = "text/html")
    public ModelAndView errorHtml(HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("contactForm", new ContactForm());
        modelAndView.addObject("searchResult", new SearchResult());
        return modelAndView;
    }

}
