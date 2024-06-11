package com.example.sandbox.batch.scheduler;

import com.example.sandbox.batch.job.quartz.QuartzJob;
import com.example.sandbox.batch.job.quartz.QuartzJobExtendsQuartJobBean;
import com.example.sandbox.batch.job.quartz.factory.AutowireQuartzJobBeanFactory;
import com.example.sandbox.service.QuartzService;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.spi.JobFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.terracotta.quartz.wrappers.JobFacade;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;


@Configuration
public class QuartzJobScheduler {
    @Bean
    public Scheduler quartJobScheduler(JobDetail jobDetail, Trigger trigger) throws SchedulerException {
//        AutowireQuartzJobBeanFactory factory = new AutowireQuartzJobBeanFactory();
//        factory.setApplicationContext(applicationContext);

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.scheduleJob(jobDetail, trigger);
//        scheduler.setJobFactory(factory);
        scheduler.start();
        return scheduler;
    }

    @Bean(name = "trigger")
    public Trigger trigger() {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/10 * * * * ?");

        return newTrigger()
                .withSchedule(cronScheduleBuilder)
                .build();
    }


    @Bean(name = "jobDetail")
    public JobDetail jobDetail(QuartzService quartzService) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("quartzService", quartzService);
        jobDataMap.put("name", "bao");

        return newJob(QuartzJob.class)
                .withIdentity("quartzJob", "group")
                .storeDurably()
                .usingJobData(jobDataMap)
                .build();
    }

//    @Bean(name = "quartzJobBeanScheduler")
    public Scheduler quartzJobBeanScheduler(
            ApplicationContext applicationContext, JobDetail quartzJobBeanJobDetail,
            Trigger quartzJobBeanTrigger) throws SchedulerException {
        SpringBeanJobFactory jobFactory = new SpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);

        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.setJobFactory(jobFactory);
        scheduler.scheduleJob(quartzJobBeanJobDetail, quartzJobBeanTrigger);

        scheduler.start();
        return scheduler;
    }

//    @Bean(name = "quartzJobBeanTrigger")
    public Trigger quartzJobBeanTrigger() {
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule("0/10 * * * * ?");

        return newTrigger()
                .withIdentity("quartzJobBeanTrigger")
                .withSchedule(cronScheduleBuilder)
                .build();
    }

//    @Bean(name = "quartzJobBeanJobDetail")
    public JobDetail quartzJobBeanJobDetail(QuartzService quartzService) {
        JobDataMap jobDataMap = new JobDataMap();
//        jobDataMap.put("quartzService", quartzService);

        return newJob(QuartzJobExtendsQuartJobBean.class)
                .withIdentity("quartzJobExtendsQuartzJobBean")
                .storeDurably()
                .usingJobData(jobDataMap)
                .build();
    }
}
