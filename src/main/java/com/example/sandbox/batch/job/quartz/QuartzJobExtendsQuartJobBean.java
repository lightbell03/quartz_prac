package com.example.sandbox.batch.job.quartz;

import com.example.sandbox.service.QuartzService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class QuartzJobExtendsQuartJobBean extends QuartzJobBean {
    @Autowired
    private QuartzService quartzService;

    protected void executeInternal(JobExecutionContext context) {
        log.info("quartzJobBean executeInternal start");
        quartzService.printLog();
        log.info("quartzJobBean executeInternal end");
    }
}
