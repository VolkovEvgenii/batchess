package ru.volkov.integration.batchess.input.itemstream;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class StateFullJobConfigaration {

    private StepBuilderFactory stepBuilderFactory;
    private JobBuilderFactory jobBuilderFactory;

    public StateFullJobConfigaration(
            StepBuilderFactory stepBuilderFactory,
            JobBuilderFactory jobBuilderFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.jobBuilderFactory = jobBuilderFactory;
    }

    @Bean
    @StepScope
    public StateFullItemReader stateFullItemReader() {
        List<String> items = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            items.add(String.valueOf(i));
        }

        return new StateFullItemReader(items);
    }

    @Bean
    public ItemWriter<String> stateFullItemWriter() {
        return list -> {
            for(String item : list) {
                System.out.println(item);
            }
        };
    }

    @Bean
    public Step stateFullStep() {
        return stepBuilderFactory.get("stateFullStep")
                .<String, String>chunk(10)
                .reader(stateFullItemReader())
                .writer(stateFullItemWriter())
                .stream(stateFullItemReader())
                .build();
    }

    @Bean
    public Job stateFullJob() {
        return jobBuilderFactory.get("stateFullJob")
                .start(stateFullStep())
                .build();
    }
}
