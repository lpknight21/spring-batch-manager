package com.edo.controller;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("job/{executionId}")
public class JobOperationController {

    private static final String EXECUTION_ID = "executionId";

    @Autowired JobExplorer jobExplorer;
    @Autowired JobOperator jobOperator;


    @RequestMapping(value = "stop")
    public BatchStatus stop(@PathVariable(EXECUTION_ID) long executionId) throws NoSuchJobExecutionException, JobExecutionNotRunningException {
        jobOperator.stop(executionId);
        return getStatus(executionId);
    }

    @RequestMapping(value = "restart")
    public BatchStatus restart(@PathVariable(EXECUTION_ID) long executionId) throws JobParametersInvalidException, JobRestartException, JobInstanceAlreadyCompleteException, NoSuchJobExecutionException, NoSuchJobException {
        return getStatus(jobOperator.restart(executionId));
    }

    @RequestMapping(value = "status")
    public BatchStatus status(@PathVariable(EXECUTION_ID) long executionId) {
        return getStatus(executionId);
    }

    private BatchStatus getStatus(@PathVariable(EXECUTION_ID) long executionId) {
        return jobExplorer.getJobExecution(executionId).getStatus();
    }
}
