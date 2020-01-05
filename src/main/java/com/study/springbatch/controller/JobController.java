package com.study.springbatch.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class JobController {
    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private List<Job> jobs;

    private Map<String, Job> jobMap;

    @PostConstruct
    public void setup() {
        this.jobMap = jobs.stream()
                .collect(Collectors.toMap(Job::getName, Function.identity()));
    }

    @GetMapping("job/{jobName}")
    public String launcher(@PathVariable String jobName, @RequestParam Map<String, String> parameters) {
        if (!jobMap.containsKey(jobName)) {
            return "NOT_FOUND_JOB";
        }

        Job job = jobMap.get(jobName);

        try {
            jobLauncher.run(job, toJobParameter(parameters));

            return "OK";

        } catch (Exception e) {
            log.error("fail to execute job, jobName : {}, parameter: {}", jobName, parameters, e);

            return "ERROR_JOB_EXECUTION";
        }
    }

    private JobParameters toJobParameter(Map<String, String> parameters) {
        JobParametersBuilder jobParametersBuilder = new JobParametersBuilder();

        for (Map.Entry<String, String> entry : parameters.entrySet()) {
            jobParametersBuilder.addString(entry.getKey(), entry.getValue());
        }

        jobParametersBuilder.addLong("executeEpochMilli", LocalDateTime.now().atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()
        );

        return jobParametersBuilder.toJobParameters();
    }
}
