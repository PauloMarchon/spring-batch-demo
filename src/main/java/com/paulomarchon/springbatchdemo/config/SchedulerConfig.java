package com.paulomarchon.springbatchdemo.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableScheduling
public class SchedulerConfig {
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private Job job;

    @Scheduled(fixedDelay = 10000, initialDelay = 2000)
    public void scheduleJob() throws Exception{
        log.info("Job scheduler start");
            jobLauncher.run(job, new JobParametersBuilder()
                .addLong("unique", System.nanoTime()).toJobParameters());
        log.info("Job scheduler finish");

    }
}
