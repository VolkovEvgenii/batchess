package ru.volkov.integration.batchess.jobflow.flow;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlowSecondConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step myStep1(){
        return stepBuilderFactory.get("myStep1")
                .tasklet(((stepContribution, chunkContext) -> {
                    System.out.println("my step was executed");
                    return RepeatStatus.FINISHED;
                })).build();
    }

    @Bean
    public Job flowLastJob(
            @Qualifier ("fooFlow") Flow flow){
        return jobBuilderFactory.get("flowLastJob")
                .start(myStep1())
                .on("COMPLETED").to(flow)
                .end()
                .build();
    }
}
