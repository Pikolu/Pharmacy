package com.pharmacy.service.impl;

import com.pharmacy.service.api.ArticleService;
import com.pharmacy.service.scheduler.AbstractJobManager;
import org.quartz.JobBuilder;
import org.quartz.SchedulerException;
import org.springframework.stereotype.Component;

/**
 * Pharmacy GmbH
 * Created by Alexander on 27.02.2016.
 */
@Component
public class ArticleJobManager extends AbstractJobManager {

    private static final String JOBS_GROUP = "Article";
    private static final int DEFAULT_INTERNAL_PERIODIC_TIME = 1;

    @Override
    protected void setupJobs() throws SchedulerException {
        JobBuilder jobBuilder = JobBuilder.newJob(ArticleJobService.class).withIdentity("articleService", JOBS_GROUP);
        saveJob(jobBuilder, DEFAULT_INTERNAL_PERIODIC_TIME);
    }

    @Override
    protected String jobGroup() {
        return JOBS_GROUP;
    }
}
