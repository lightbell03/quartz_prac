package com.example.sandbox.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class QuartzService {
    public String printLog() {
        log.info("quartzService print log");

        return "ok printLog()";
    }
}
