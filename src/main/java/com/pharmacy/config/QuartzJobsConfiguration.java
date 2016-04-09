package com.pharmacy.config;

import com.pharmacy.service.impl.ArticleJobManager;
import com.pharmacy.service.impl.SitemapJobManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Pharmacy GmbH
 * Created by Alexander on 27.02.2016.
 */
@Configuration
public class QuartzJobsConfiguration {

    @Autowired
    private ArticleJobManager articleJobManager;
    @Autowired
    private SitemapJobManager sitemapJobManager;

    @PostConstruct
    public void setupJobs() {
        articleJobManager.initialize();
        sitemapJobManager.initialize();
    }
}
