package ru.volkov.integration.batchess.output.common;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SysOutJobConfiguration {

    private StepBuilderFactory stepBuilderFactory;
    private JobBuilderFactory jobBuilderFactory;

    public SysOutJobConfiguration(
            StepBuilderFactory stepBuilderFactory,
            JobBuilderFactory jobBuilderFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.jobBuilderFactory = jobBuilderFactory;
    }

    @Bean
    public ListItemReader<String> sysOutItemReader() {
        List<String> items = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            items.add(String.valueOf(i));
        }

        return new ListItemReader<>(items);
    }

    @Bean
    public SysOutItemWriter sysOutItemWriter() {
        return new SysOutItemWriter();
    }

    @Bean
    public Step sysOutStep() {
        return stepBuilderFactory.get("sysOutStep")
                .<String, String> chunk(10)
                .reader(sysOutItemReader())
                .writer(sysOutItemWriter())
                .build();
    }

    @Bean
    public Job sysOutJob() {
        return jobBuilderFactory.get("sysOutJob")
                .start(sysOutStep())
                .build();
    }

}
