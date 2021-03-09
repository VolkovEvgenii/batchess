package ru.volkov.integration.batchess.input.multiplefile;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class MultipleFileJobConfiguration {

    private StepBuilderFactory stepBuilderFactory;
    private JobBuilderFactory jobBuilderFactory;

    @Value("classpath*:/multidata/customer*.csv")
    private Resource[] inputFiles;

    public MultipleFileJobConfiguration(
            StepBuilderFactory stepBuilderFactory,
            JobBuilderFactory jobBuilderFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.jobBuilderFactory = jobBuilderFactory;
    }

    @Bean
    public MultiResourceItemReader<MultipleFileCustomer> multiResourceItemReader() {
        MultiResourceItemReader<MultipleFileCustomer> reader = new MultiResourceItemReader<>();
        reader.setDelegate(itemReader());
        reader.setResources(inputFiles);
        return reader;
    }

    @Bean
    public FlatFileItemReader<MultipleFileCustomer> itemReader() {
        FlatFileItemReader<MultipleFileCustomer> reader = new FlatFileItemReader<>();

        DefaultLineMapper<MultipleFileCustomer> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setNames(new String[] {"id", "name", "cardBskNum"});
        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(new MultipleFileCustomerFielSetMapper());
        lineMapper.afterPropertiesSet();

        reader.setLineMapper(lineMapper);
        return reader;
    }

    @Bean
    public ItemWriter<MultipleFileCustomer> multipleFileItemWriter() {
        return list -> {
            for(MultipleFileCustomer item : list) {
                System.out.println(item.toString());
            }
        };
    }

    @Bean
    public Step multiFileStep() {
        return stepBuilderFactory.get("multiFileStep")
                .<MultipleFileCustomer, MultipleFileCustomer> chunk(10)
                .reader(multiResourceItemReader())
                .writer(multipleFileItemWriter())
                .build();
    }

    @Bean
    public Job multiFileJob() {
        return jobBuilderFactory.get("multiFileJob")
                .start(multiFileStep())
                .build();
    }
}
