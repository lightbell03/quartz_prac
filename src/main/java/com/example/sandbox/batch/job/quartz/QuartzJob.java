package com.example.sandbox.batch.job.quartz;

import com.example.sandbox.service.QuartzService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class QuartzJob implements Job {
    private QuartzService quartzService;
    private String test;

    public QuartzJob() {
    }

    public void setQuartzService(QuartzService quartzService) {
        this.quartzService = quartzService;
    }

    public void setTest(String test) {
        this.test = test;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("start execute quartz job");
        quartzService.printLog();
        log.info("end execute quartz job");
    }
}
