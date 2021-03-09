package ru.volkov.integration.batchess.output.dbwriter;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
public class DbWriterJobConfiguration {

    private StepBuilderFactory stepBuilderFactory;
    private JobBuilderFactory jobBuilderFactory;
    private DataSource dataSource;

    public DbWriterJobConfiguration(
            StepBuilderFactory stepBuilderFactory,
            JobBuilderFactory jobBuilderFactory,
            DataSource dataSource) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.jobBuilderFactory = jobBuilderFactory;
        this.dataSource = dataSource;
    }

    @Bean
    public FlatFileItemReader<DbWriterCustomer> dbWriterItemReader() {
        FlatFileItemReader<DbWriterCustomer> reader = new FlatFileItemReader<>();

        reader.setLinesToSkip(1);
        reader.setResource(new ClassPathResource("/temp/customers.csv"));

        DefaultLineMapper<DbWriterCustomer> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();

        tokenizer.setNames(new String[] {"id", "name", "cardBskNum"});

        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(new DbWriterCustomerFieldSetMapper());
        lineMapper.afterPropertiesSet();

        reader.setLineMapper(lineMapper);

        return reader;
    }

    @Bean
    public JdbcBatchItemWriter<DbWriterCustomer> dbWriterItemWriter() {
        String sqlLine = "INSERT INTO test.customers VALUES (:id, :name, :cardBskNum)";
        JdbcBatchItemWriter<DbWriterCustomer> writer = new JdbcBatchItemWriter<>();
        writer.setSql(sqlLine);
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setDataSource(this.dataSource);
        writer.afterPropertiesSet();

        return writer;
    }

    @Bean
    public Step dbWriterStep() {
        return stepBuilderFactory.get("dbWriterStep")
                .<DbWriterCustomer, DbWriterCustomer> chunk(10)
                .reader(dbWriterItemReader())
                .writer(dbWriterItemWriter())
                .build();
    }

    @Bean
    public Job dbWriterJob() {
        return jobBuilderFactory.get("dbWriterJob")
                .start(dbWriterStep())
                .build();
    }
}
