package com.pharmacy.service.scheduler;

import org.quartz.*;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.Set;

/**
 * Pharmacy GmbH
 * Created by Alexander on 27.02.2016.
 */
public abstract class AbstractJobManager {

    @Autowired
    private Scheduler scheduler;

    private Set<JobKey> jobsToDelete;

    @Transactional
    public void initialize() {

        try {
            jobsToDelete = scheduler.getJobKeys(GroupMatcher.jobGroupEquals(jobGroup()));

            for (JobKey jobKey : jobsToDelete) {
                scheduler.deleteJob(jobKey);
            }
            
            setupJobs();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    public void saveJob(JobBuilder jobBuilder, int defaultInterval) throws SchedulerException {
        JobDetail jobDetail = jobBuilder.build();
        JobKey jobKey = jobDetail.getKey();
        if (!jobGroup().equals(jobKey.getGroup())) {
            throw new IllegalArgumentException(String.format("Job must habe group '%s', but has '%s'", jobGroup(), jobKey.getGroup()));
        }

        TriggerKey triggerKey = triggerKey(jobKey);
        TriggerBuilder triggerBuilder = TriggerBuilder.newTrigger().withIdentity(triggerKey).forJob(jobKey);
        scheduler.addJob(jobDetail, true, true);

        Trigger existingTrigger = scheduler.getTrigger(triggerKey);

        if (existingTrigger != null) {
            triggerBuilder.withSchedule(existingTrigger.getScheduleBuilder());
            scheduler.rescheduleJob(triggerKey, triggerBuilder.build());
        } else {
            triggerBuilder.withSchedule(repeatingSchedule(defaultInterval));
            scheduler.scheduleJob(triggerBuilder.build());
        }
    }

    private ScheduleBuilder repeatingSchedule(int defaultInterval) {
        return SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(defaultInterval).repeatForever();
    }

    private TriggerKey triggerKey(JobKey jobKey) {
        return new TriggerKey(jobKey.getName(), triggerGroup(jobKey));
    }

    private String triggerGroup(JobKey jobKey) {
        return jobKey.getGroup() + "." + jobKey.getName();
    }

    protected abstract void setupJobs() throws SchedulerException;

    protected abstract String jobGroup();
}
