package ru.volkov.integration.batchess.decider;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DeciderConfiguration {

    private JobBuilderFactory jobBuilderFactory;
    private StepBuilderFactory stepBuilderFactory;


    @Autowired
    public DeciderConfiguration(
            JobBuilderFactory jobBuilderFactory,
            StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job deciderJob() {
        return jobBuilderFactory.get("deciderJob")
                .start(deciderStartStep())
                .next(decider())
                .from(decider()).on("ODD").to(deciderOddStep())
                .from(decider()).on("EVEN").to(deciderEvenStep())
                .from(deciderOddStep()).on("*").to(decider())
/*                .from(decider()).on("ODD").to(deciderOddStep())
                .from(decider()).on("EVEN").to(deciderEvenStep())*/
                .end()
                .build();
    }

    @Bean
    public Step deciderStartStep(){
        return stepBuilderFactory.get("deciderStartStep")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("This is the start tasklet");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step deciderEvenStep(){
    return stepBuilderFactory.get("deciderEvenStep")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("This is the even tasklet");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public Step deciderOddStep(){
        return stepBuilderFactory.get("deciderOddStep")
                .tasklet((stepContribution, chunkContext) -> {
                    System.out.println("This is the odd tasklet");
                    return RepeatStatus.FINISHED;
                }).build();
    }

    @Bean
    public JobExecutionDecider decider(){
        return new OddDecider();
    }


    public static class OddDecider implements JobExecutionDecider{
        private int counter = 0;

        @Override
        public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
            counter ++;

            if (counter % 2 == 0) {
                return new FlowExecutionStatus("EVEN");
            } else {
                return new FlowExecutionStatus("ODD");
            }
        }
    }
}
