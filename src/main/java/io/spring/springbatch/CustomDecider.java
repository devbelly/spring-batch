package io.spring.springbatch;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

public class CustomDecider implements JobExecutionDecider {
    private int number;
    @Override

    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        number++;
        if(number%2==0){
            return new FlowExecutionStatus("EVEN");
        }
        else{
            return new FlowExecutionStatus("ODD");
        }

    }
}
