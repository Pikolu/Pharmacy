package com.pharmacy.service.impl;

import com.pharmacy.service.api.SitemapGeneratorService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Pharmacy GmbH
 * Created by Alexander on 09.04.2016.
 */
public class SitemapGeneratorJobService implements Job {

    @Autowired
    private SitemapGeneratorService sitemapGeneratorService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        sitemapGeneratorService.generateSitemap();
    }
}
