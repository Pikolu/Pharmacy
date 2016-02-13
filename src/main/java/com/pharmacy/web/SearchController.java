package com.pharmacy.web;

import com.pharmacy.domain.Article;
import com.pharmacy.domain.SearchResult;
import com.pharmacy.domain.User;
import com.pharmacy.repository.utils.FilterOptions;
import com.pharmacy.repository.utils.SortOrder;
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
     * @param pageable  selected or current page.
     * @return
     */
    @RequestMapping(value = "suche", method = RequestMethod.GET)
    public String search(Model model, Pageable pageable, @ModelAttribute("searchResult") SearchResult searchResult) {
        FacetedPage<Article> page = articleService.findArticlesByParameter(searchResult.getParameter(), pageable, searchResult);
        searchResult.setPage(page);
        model.addAttribute("searchResult", searchResult);
        model.addAttribute("urlEncoder", new URLHelper());
        model.addAttribute("articleHelper", new ArticleHelper());
        model.addAttribute("sortOrders", SortOrder.values());
        return "search";
    }
}
