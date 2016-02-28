package com.pharmacy.service.impl;

import com.pharmacy.service.api.ExporterService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Pharmacy GmbH
 * Created by Alexander on 28.02.2016.
 */
public class ArticleJobService implements Job {

    @Autowired
    private ExporterService exporterService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        exporterService.exportArticles();
    }
}
