package com.edo.controller;

import com.edo.model.ParameterPair;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("job")
public class JobController {

    @Autowired
    JobLauncher jobLauncher;

    @Autowired
    ApplicationContext applicationContext;

    @RequestMapping(value = "start/{jobName}", method = RequestMethod.POST)
    public JobInstance startJob(@PathVariable String jobName, @RequestBody List<ParameterPair> parameterPairs) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        Job job = (Job) applicationContext.getBean(jobName);
        JobExecution jobExecution = jobLauncher.run(job, new JobParameters(getJobParameters(parameterPairs)));
        return jobExecution.getJobInstance();
    }

    private Map<String, JobParameter> getJobParameters(List<ParameterPair> parameterPairList) {
        Map<String, JobParameter> jobParameterMap = new HashMap<>();
        for(ParameterPair parameterPair : parameterPairList) {
            jobParameterMap.put(parameterPair.getName(), new JobParameter(parameterPair.getValue()));
        }
        return jobParameterMap;
    }
}
