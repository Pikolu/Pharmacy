package com.pharmacy.web;

import com.pharmacy.domain.Article;
import com.pharmacy.domain.SearchResult;
import com.pharmacy.domain.User;
import com.pharmacy.repository.utils.FilterOptions;
import com.pharmacy.service.api.ArticleService;
import com.pharmacy.web.helper.ArticleHelper;
import com.pharmacy.web.helper.URLHelper;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.FacetedPage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
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
//    @RequestMapping(value = "suche", method = RequestMethod.GET)
//    public
//    @ResponseBody
//    ModelAndView search(@RequestParam String parameter, @RequestParam(required = false) String pharmacyName, Pageable pageable) {
//        ModelAndView resultView = new ModelAndView("search");
//        FilterOptions filterOptions = new FilterOptions();
//        if (StringUtils.isNotEmpty(pharmacyName)) {
//            String[] names = pharmacyName.split(":");
//            LOG.info("names {}", names);
//            filterOptions.setPharmacies(Lists.newArrayList(names));
//        } else {
//            pharmacyName = new String();
//        }
//        FacetedPage<Article> page = articleService.findArticlesByParameter(parameter, pageable, filterOptions);
//        resultView.addObject("searchResult", new SearchResult<>(page, filterOptions));
//        resultView.addObject("parameter", parameter);
//        resultView.addObject("urlEncoder", new URLHelper());
//        resultView.addObject("articleHelper", new ArticleHelper());
//        resultView.addObject("pharmacyName", pharmacyName);
//        return resultView;
//    }

//    @RequestMapping(value = "suche", method = RequestMethod.GET)
//    public String search(Model model, @RequestParam String parameter, Pageable pageable) {
//                FilterOptions filterOptions = new FilterOptions();
//        FacetedPage<Article> page = articleService.findArticlesByParameter(parameter, pageable, filterOptions);
//        model.addAttribute("searchResult", new SearchResult(page, filterOptions));
//        model.addAttribute("parameter", parameter);
//        model.addAttribute("urlEncoder", new URLHelper());
//        model.addAttribute("articleHelper", new ArticleHelper());
////        model.addAttribute("pharmacyName", pharmacyName);
//        return "search";
//    }


    @RequestMapping(value = "suche", method = RequestMethod.GET)
    public String search(Model model, Pageable pageable, @ModelAttribute("searchResult") SearchResult searchResult) {
        FacetedPage<Article> page = articleService.findArticlesByParameter(searchResult.getParameter(), pageable, searchResult);
        searchResult.setPage(page);
        model.addAttribute("searchResult", searchResult);
        model.addAttribute("urlEncoder", new URLHelper());
        model.addAttribute("articleHelper", new ArticleHelper());
        return "search";
    }
}
