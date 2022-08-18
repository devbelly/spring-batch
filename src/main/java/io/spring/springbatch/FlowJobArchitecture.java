package io.spring.springbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class FlowJobArchitecture {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("batchJob")
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        System.out.println(">> beforeJob Executed");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        System.out.println(">> afterJob Executed");
                    }
                })
                .start(flow())
                .next(step3())
                .end().build();
    }

    @Bean
    public Flow flow() {
        FlowBuilder<Flow> flowBuilder = new FlowBuilder<>("flow");

        flowBuilder
                .start(step1()).next(step2()).end();

        return flowBuilder.build();

    }


    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1").tasklet(((stepContribution, chunkContext) -> {
            System.out.println(">> step1");
            return null;
        })).build();
    }

    @Bean
    public Step step2() {
        return stepBuilderFactory
                .get("step2").tasklet(((stepContribution, chunkContext) -> {
                    System.out.println(">> step2");
                    return null;
                })).build();
    }

    @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3").tasklet(((stepContribution, chunkContext) -> {
            System.out.println(">> step3");
            return null;
        })).build();
    }


}


