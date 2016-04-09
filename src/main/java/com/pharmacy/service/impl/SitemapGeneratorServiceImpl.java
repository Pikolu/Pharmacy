package com.pharmacy.service.impl;

import com.google.common.collect.Lists;
import com.pharmacy.domain.Article;
import com.pharmacy.service.api.ArticleService;
import com.pharmacy.service.api.SitemapGeneratorService;
import com.redfin.sitemapgenerator.ChangeFreq;
import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.WebSitemapUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

/**
 * Pharmacy GmbH
 * Created by Alexander on 09.04.2016.
 */
@Service
@Transactional
public class SitemapGeneratorServiceImpl implements SitemapGeneratorService {

    private static final Logger LOG = LoggerFactory.getLogger(PharmacyServiceImpl.class);

    @Inject
    private ArticleService articleService;

    @Override
    public void generateSitemap() {
        String baseUrl = "https://localhost:8443/";
        try {
            WebSitemapGenerator wsg = new WebSitemapGenerator(baseUrl, new File("D:\\Workspace\\Pharmacy\\src\\main\\webapp"));

            // Index
            WebSitemapUrl indexUrl = new WebSitemapUrl.Options(baseUrl)
                    .lastMod(new Date()).priority(1.0).changeFreq(ChangeFreq.WEEKLY).build();
            wsg.addUrl(indexUrl); // repeat multiple times

            // Articles
            Iterable<Article> articles = articleService.findAll();
            List<Article> a = Lists.newArrayList(articles);
            LOG.info("Sitemap generator is called. Export items => {}", a.size());
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

                if (sitemaps.size() > 1) {
                    wsg.writeSitemapsWithIndex();
                }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
