package com.pharmacy.config;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import javax.sql.DataSource;
import java.util.Properties;
import java.util.concurrent.Executor;

/**
 * Pharmacy GmbH
 * Created by Alexander on 27.02.2016.
 */
@Configuration
public class QuartzSchedulerConfiguration implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private Executor executor;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private ApplicationContext applicationContext;

    private SchedulerFactoryBean schedulerFactoryBean;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() {

        Properties properties = new Properties();

        properties.setProperty("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.PostgreSQLDelegate");

        AutowiringSpringBeanJobFactoriy autowiringSpringBeanJobFactoriy = new AutowiringSpringBeanJobFactoriy();
        autowiringSpringBeanJobFactoriy.setApplicationContext(applicationContext);

        schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setQuartzProperties(properties);
        schedulerFactoryBean.setAutoStartup(false);
        schedulerFactoryBean.setDataSource(dataSource);
        schedulerFactoryBean.setJobFactory(autowiringSpringBeanJobFactoriy);
        schedulerFactoryBean.setTaskExecutor(executor);
        schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(true);
        return schedulerFactoryBean;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        schedulerFactoryBean.start();
    }


    private static class AutowiringSpringBeanJobFactoriy extends SpringBeanJobFactory implements ApplicationContextAware {

        private transient AutowireCapableBeanFactory beanFactory;

        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            beanFactory = applicationContext.getAutowireCapableBeanFactory();
        }

        @Override
        protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
            Object job = super.createJobInstance(bundle);
            beanFactory.autowireBean(job);
            return job;
        }
    }

}
