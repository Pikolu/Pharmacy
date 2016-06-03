package com.pharmacy.service.impl;

import com.google.common.collect.Lists;
import com.pharmacy.config.Constants;
import com.pharmacy.domain.Article;
import com.pharmacy.repository.ArticleRepository;
import com.pharmacy.repository.search.ArticleSearchRepository;
import com.pharmacy.service.api.ArticleService;
import com.pharmacy.service.api.SitemapGeneratorService;
import com.redfin.sitemapgenerator.ChangeFreq;
import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.WebSitemapUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
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
    @Inject
    private ArticleRepository articleRepository;
    @Inject
    private ArticleSearchRepository articleSearchRepository;

    private boolean enable;
    private String sitemapPath;


    @Override
    public void generateSitemap() {
        if (this.enable) {
            try {
                final WebSitemapGenerator wsg = new WebSitemapGenerator(Constants.BASE_URL, new File(this.sitemapPath));

                // Index
                final WebSitemapUrl indexUrl = new WebSitemapUrl.Options(Constants.BASE_URL)
                        .lastMod(new Date()).priority(1.0).changeFreq(ChangeFreq.WEEKLY).build();
                wsg.addUrl(indexUrl); // repeat multiple times

                // Articles
                final List<Object[]> articles = articleRepository.findAllForSiteMap();
                LOG.info("Site map generator is called. Export items => {}", articles.size());
                articles.forEach(e -> {
                    try {
                        final WebSitemapUrl url = new WebSitemapUrl.Options(Constants.BASE_URL + "/preisvergleich/" + e[0] + "/" + URLEncoder.encode((String) e[1], "UTF-8"))
                                .lastMod(new Date()).priority(1.0).changeFreq(ChangeFreq.WEEKLY).build();
                        wsg.addUrl(url); // repeat multiple times
                    } catch (MalformedURLException | UnsupportedEncodingException ex) {
                        ex.printStackTrace();
                    }
                });
                final List<File> siteMaps = wsg.write();

                if (siteMaps.size() > 1) {
                    wsg.writeSitemapsWithIndex();
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    }

    @Value("${pharmacy.enable}")
    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Value("${pharmacy.sitemapPath}")
    public void setSitemapPath(String sitemapPath) {
        this.sitemapPath = sitemapPath;
    }
}
