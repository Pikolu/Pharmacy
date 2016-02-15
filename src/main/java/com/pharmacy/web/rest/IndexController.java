package com.pharmacy.web.rest;

import com.pharmacy.domain.Article;
import com.pharmacy.domain.SearchResult;
import com.pharmacy.service.api.ArticleService;
import com.pharmacy.service.api.ImportService;
import com.pharmacy.web.helper.ArticleHelper;
import com.pharmacy.web.helper.URLHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by Alexander on 09.11.2015.
 */
@Controller
public class IndexController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(IndexController.class);

    @Inject
    private ImportService importService;
    @Inject
    private ArticleService articleService;

    @RequestMapping(value = {"/", "/index", "/welcome**"}, method = RequestMethod.GET)
    public ModelAndView index() {
        List<Article> articles = articleService.loadBestDiscountedArticles();
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("searchResult", new SearchResult());
        modelAndView.addObject("articles", articles);
        modelAndView.addObject("articleHelper", new ArticleHelper());
        modelAndView.addObject("urlEncoder", new URLHelper());
        return modelAndView;
    }


    @RequestMapping("/importtest")
    public void importTest(Model model) {
        Assert.notNull(model);
        importService.importCSVFile();
    }
}
