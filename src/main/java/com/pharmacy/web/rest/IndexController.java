package com.pharmacy.web.rest;

import com.pharmacy.domain.Article;
import com.pharmacy.domain.SearchResult;
import com.pharmacy.service.api.ArticleService;
import com.pharmacy.service.api.ImportService;
import com.pharmacy.web.helper.ArticleHelper;
import com.pharmacy.web.helper.URLHelper;
import com.redfin.sitemapgenerator.ChangeFreq;
import com.redfin.sitemapgenerator.SitemapIndexGenerator;
import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.WebSitemapUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

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
    public ModelAndView index(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        List<Article> articles = articleService.loadBestDiscountedArticles();
        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addAllObjects(parameterMap);
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

//    @RequestMapping("/robots.txt")
//    @ResponseBody
//    public String getRobotsTXT() {
//        StringBuilder result = new StringBuilder();
//        result.append("User-agent: ").append("*")
//                .append("\n")
//                .append("Disallow:");
//        return result.toString();
//    }

    @RequestMapping("/sitemap_generator")
    public
    @ResponseBody
    String generateSitemap(HttpServletRequest request) {

        String baseUrl = request.getScheme() + // "http"
                "://" +                                // "://"
                request.getServerName() +              // "myhost"
                ":" +                                  // ":"
                request.getServerPort();               // "80"

        try {
            WebSitemapGenerator wsg = new WebSitemapGenerator(baseUrl, new File("D:\\Workspace\\Pharmacy\\src\\main\\webapp"));
            Iterable<Article> articles = articleService.findAll();
            articles.forEach(e -> {
                try {
                    WebSitemapUrl url = new WebSitemapUrl.Options(baseUrl + "/preisvergleich/" + e.getId() + "/" + URLEncoder.encode(e.getName(), "UTF-8"))
                            .lastMod(new Date()).priority(1.0).changeFreq(ChangeFreq.WEEKLY).build();
                    wsg.addUrl(url); // repeat multiple times
                } catch (MalformedURLException | UnsupportedEncodingException ex) {
                    ex.printStackTrace();
                }
            });
            List<File> sitemaps = wsg.write();

            if (sitemaps.size() > 1){
                wsg.writeSitemapsWithIndex();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return "";
    }

}
