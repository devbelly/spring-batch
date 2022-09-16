package io.spring.springbatch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Configuration
public class ItemStreamConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("batchJob")
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .chunk(5)
                .reader(itemReader())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public ItemWriter itemWriter() {
        return new CustomItemWriter();
    }


    @Bean
    public CustomItemStreamReader<String> itemReader() {
        List<String> items = new ArrayList<>(10);
        for (int i = 0; i < 10; ++i) {
            items.add(String.valueOf(i));
        }
        return new CustomItemStreamReader(items);
    }
}
