package com.pharmacy.web;

import com.pharmacy.domain.Article;
import com.pharmacy.service.api.ArticleService;
import com.pharmacy.web.helper.ArticleHelper;
import com.pharmacy.web.helper.URLHelper;
import org.apache.commons.lang.StringUtils;
import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.FacetedPage;
import org.springframework.data.elasticsearch.core.FacetedPageImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Alexander on 12.11.2015.
 */
@Controller
public class SearchController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(IndexController.class);

    @Inject
    private ArticleService articleService;

    /**
     * This method searched the articles for the result list in search field.
     *
     * @param parameter for search the articles
     * @param pageable  selected or current page.
     * @return
     */
    @RequestMapping(value = "suche", method = RequestMethod.GET)
    public
    @ResponseBody
    ModelAndView search(@RequestParam String parameter, @RequestParam(required = false) String pharmacyName, Pageable pageable) {
        ModelAndView resultView = new ModelAndView("search");

        FacetedPage<Article> page = articleService.findArticlesByParameter(parameter, pageable);
        if (StringUtils.isNotEmpty(pharmacyName)) {
            String[] names = pharmacyName.split(":");
            LOG.info("names {}", names);
        } else {
            pharmacyName = new String();
//            page = articleService.findArticlesByParameter(parameter, pageable);
        }

        resultView.addObject("page", page);
        resultView.addObject("parameter", parameter);
        resultView.addObject("urlEncoder", new URLHelper());
        resultView.addObject("articleHelper", new ArticleHelper());
        resultView.addObject("pharmacyName", pharmacyName);
        return resultView;
    }

    @RequestMapping(value = "/live_suche", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Article> search(HttpServletRequest request, @RequestParam String parameter,
                         @RequestParam(required = false) String pharmacyName) {
        List<Article> articles = null;
        try {
            LOG.info("SEARCH_REQUEST: {}", parameter);
            articles = null; //articleService.findArticlesByParameter(parameter);
        } catch (ServiceException ex) {
            ex.fillInStackTrace();
        }
        return articles;
    }
}
