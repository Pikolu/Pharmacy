package com.pharmacy.web.rest;

import com.pharmacy.domain.Article;
import com.pharmacy.domain.SearchResult;
import com.pharmacy.repository.utils.SortOrder;
import com.pharmacy.service.api.ArticleService;
import com.pharmacy.web.helper.ArticleHelper;
import com.pharmacy.web.helper.URLHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.FacetedPage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;

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

        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        if (pageNumber > 0) {
            pageNumber = pageNumber - 1;
        }
        Pageable newPageable = new PageRequest(pageNumber, pageSize);
        FacetedPage<Article> page = articleService.findArticlesByParameter(searchResult.getParameter(), newPageable, searchResult);
        searchResult.setFacetedPage(page);
        searchResult.buildPagination(pageable.getPageNumber(), model, page.getTotalElements());
        model.addAttribute("searchResult", searchResult);
        model.addAttribute("urlEncoder", new URLHelper());
        model.addAttribute("articleHelper", new ArticleHelper());
        model.addAttribute("sortOrders", SortOrder.values());
        return "search";
    }
}
