package com.example.sandbox.batch.scheduler;

import com.example.sandbox.batch.job.quartz.QuartzJob;
import java.util.List;
import java.util.stream.Collectors;
import org.quartz.CronTrigger;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@Configuration
public class QuartzJobSchedulerUsingFactoryBean {
    @Bean(name = "schedulerFactoryBean")
    public Scheduler scheduler(List<CronTriggerFactoryBean> cronTriggerFactoryBeanList) {
        SchedulerFactoryBean schedulerFactory = new SchedulerFactoryBean();
        Trigger
        schedulerFactory.setTriggers(cronTriggerFactoryBeanList.stream().map(CronTriggerFactoryBean::getObject).toArray());

        return schedulerFactory.getScheduler();
    }

    @Bean(name = "triggerFactoryBean")
    public CronTriggerFactoryBean trigger(JobDetailFactoryBean jobDetailFactoryBean) {
        CronTriggerFactoryBean factoryBean = new CronTriggerFactoryBean();
        factoryBean.setCronExpression("0/10 * * * * ?");
        factoryBean.setJobDetail(jobDetailFactoryBean.getObject());

        return factoryBean;
    }

    @Bean(name = "jobDetailFactoryBean")
    public JobDetailFactoryBean jobDetailFactoryBean() {
        JobDetailFactoryBean factoryBean = new JobDetailFactoryBean();
        factoryBean.setJobClass(QuartzJob.class);

        return factoryBean.getObject();
    }
}
