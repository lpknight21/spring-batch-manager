package com.edo.controller;

import com.edo.model.ParameterPair;
import org.springframework.batch.core.*;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
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
    JobOperator jobOperator;

    @Autowired
    JobExplorer jobExplorer;

    @Autowired
    ApplicationContext applicationContext;

    @RequestMapping(value = "start/{jobName}", method = RequestMethod.POST)
    public JobInstance startJob(@PathVariable String jobName, @RequestBody List<ParameterPair> parameterPairs) throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobInstanceAlreadyExistsException, NoSuchJobException {
        Job job = (Job) applicationContext.getBean(jobName);
        Long executionId = jobOperator.start(jobName, getJobParametersToString(parameterPairs));
        JobExecution jobExecution = jobExplorer.getJobExecution(executionId);
        return jobExecution.getJobInstance();
    }

    private Map<String, JobParameter> getJobParameters(List<ParameterPair> parameterPairList) {
        Map<String, JobParameter> jobParameterMap = new HashMap<>();
        for(ParameterPair parameterPair : parameterPairList) {
            jobParameterMap.put(parameterPair.getName(), new JobParameter(parameterPair.getValue()));
        }
        return jobParameterMap;
    }

    private String getJobParametersToString(List<ParameterPair> parameterPairList) {
        String parameters = "";
        for(ParameterPair parameterPair : parameterPairList) {
            parameters = parameters + parameterPair.getName() + "=" + parameterPair.getValue() + "\n";
        }
        return parameters;
    }
}
