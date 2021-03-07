package ru.volkov.integration.batchess.jobflow.flow;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlowConfiguration {

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Bean
    public Step flowStep1(){
        return stepBuilderFactory.get("flowStep")
                .tasklet(((stepContribution, chunkContext) -> {
                    System.out.println("flowStep from inside flow foo");
                    return RepeatStatus.FINISHED;
                })).build();
    }

    @Bean
    public Step flowStep2(){
        return stepBuilderFactory.get("flowStep2")
                .tasklet(((stepContribution, chunkContext) -> {
                    System.out.println("flowStep2 from inside flow foo");
                    return RepeatStatus.FINISHED;
                })).build();
    }

    @Bean
    @Qualifier("fooFlow")
    public Flow foo(){
        FlowBuilder<Flow> flowFlowBuilder = new FlowBuilder<>("fooFlow");

        flowFlowBuilder
                .start(flowStep1())
                .next(flowStep2())
                .end();

        return flowFlowBuilder.build();
    }
}
