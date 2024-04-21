package com.skyblue.apiconsultas.controller;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sunat")
@RequiredArgsConstructor
public class SunatController {

    private final JobLauncher jobLauncher;
    private final Job job;

    @SneakyThrows
    @PostMapping
    public void importTxtToDbJob()  {
        JobParameters jobParameter  = new JobParametersBuilder()
                .addLong("startAt", System.currentTimeMillis())
                .toJobParameters();
        jobLauncher.run(job,jobParameter);
    }
}
