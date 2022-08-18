package io.spring.springbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class JobExecutionDeciderConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job(){
        return jobBuilderFactory.get("batchJob")
                .incrementer(new RunIdIncrementer())
                .start(step())
                .next(decider())
                .from(decider()).on("ODD").to(oddStep())
                .from(decider()).on("EVEN").to(evenStep())
                .end()
                .build();
    }

    @Bean
    public Step step(){
        return stepBuilderFactory.get("step")
                .tasklet(((stepContribution, chunkContext) -> {
                    System.out.println(">> startStep");
                    return null;
                }))
                .build();
    }

    @Bean
    public Step evenStep(){
        return stepBuilderFactory.get("evenStep")
                .tasklet(((stepContribution, chunkContext) -> {
                    System.out.println(">> evenStep");
                    return null;
                }))
                .build();
    }
    @Bean
    public Step oddStep(){
        return stepBuilderFactory.get("oddStep")
                .tasklet(((stepContribution, chunkContext) -> {
                    System.out.println(">> oddStep");
                    return null;
                }))
                .build();
    }


    @Bean
    public JobExecutionDecider decider(){
        return new CustomDecider();
    }
}
