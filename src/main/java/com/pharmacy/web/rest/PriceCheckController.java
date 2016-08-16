package com.pharmacy.web.rest;

import com.google.common.collect.Lists;
import com.pharmacy.domain.Article;
import com.pharmacy.domain.Price;
import com.pharmacy.domain.SearchResult;
import com.pharmacy.exceptions.ServiceException;
import com.pharmacy.service.api.ArticleService;
import com.pharmacy.service.api.PriceService;
import com.pharmacy.web.helper.ArticleHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Alexander on 03.01.2016.
 */
@Controller
public class PriceCheckController extends AbstractController {

    @Inject
    private ArticleService articleService;
    @Inject
    private PriceService priceService;

    @RequestMapping(value = "/preisvergleich/{articelNumber}/{name}", method = RequestMethod.GET)
    public ModelAndView loadAllPharmacyForPriceCheck(@PathVariable String articelNumber, @PathVariable String name, HttpServletRequest request, HttpSession session) {
        ModelAndView modelAndView = new ModelAndView("priceCheck");
        try {
            Article article = articleService.findArticleByArticleNumber(Long.valueOf(articelNumber));
            List<Price> prices = priceService.loadPricesForArticle(article);
            ArticleHelper.sortPrice(prices);
            modelAndView.addObject("article", article);
            modelAndView.addObject("prices", prices);
            fillModel(modelAndView);
        } catch (ServiceException | NumberFormatException e) {
        }
        return modelAndView;
    }
}
