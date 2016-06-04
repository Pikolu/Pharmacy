package com.pharmacy.service.impl;

import com.pharmacy.service.scheduler.AbstractJobManager;
import org.quartz.JobBuilder;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Component;

/**
 * Pharmacy GmbH
 * Created by Alexander on 09.04.2016.
 */
@Component
public class SitemapJobManager extends AbstractJobManager {

    private static final String JOBS_GROUP = "Sitemap";
    private static final int DEFAULT_INTERNAL_PERIODIC_TIME = 60;

    @Override
    protected void setupJobs() throws SchedulerException {

        JobBuilder jobBuilder = JobBuilder.newJob(SitemapGeneratorJobService.class).withIdentity("sitemapGeneratorJobService", JOBS_GROUP);
        saveJob(jobBuilder, DEFAULT_INTERNAL_PERIODIC_TIME);
    }

    @Override
    protected String jobGroup() {
        return JOBS_GROUP;
    }
}
