package ru.volkov.integration.batchess.input.itemreader;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class StatelessJobConfiguration {

    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    public StatelessJobConfiguration(
            JobBuilderFactory jobBuilderFactory,
            StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public StatelessItemReader statelessItemReader() {
        List<String> data = new ArrayList<>();

        data.add("foo");
        data.add("bar");
        data.add("baz");

        return new StatelessItemReader(data);
    }

    @Bean
    public Step statelessStep() {
        return stepBuilderFactory.get("statlessStep")
                .<String, String>chunk(2)
                .reader(statelessItemReader())
                .writer(list -> {
                    for (String item : list) {
                        System.out.println(item);
                    }
                }).build();
    }

    @Bean
    public Job statelessJob() {
        return jobBuilderFactory.get("statelessJob")
                .start(statelessStep())
                .build();
    }
}
