package ru.volkov.integration.batchess.output.flatfile;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

import javax.sql.DataSource;
import java.io.File;

@Configuration
public class FlatFileJobConfigurationSG {

    private StepBuilderFactory stepBuilderFactory;
    private JobBuilderFactory jobBuilderFactory;
    private DataSource dataSource;

    public FlatFileJobConfigurationSG(
            StepBuilderFactory stepBuilderFactory,
            JobBuilderFactory jobBuilderFactory,
            DataSource dataSource) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.jobBuilderFactory = jobBuilderFactory;
        this.dataSource = dataSource;
    }

    @Bean
    public JdbcCursorItemReader<FlatFileStudentGroup> flatFileItemReaderSG() {

        JdbcCursorItemReader<FlatFileStudentGroup> reader= new JdbcCursorItemReader<>();
        String sql = "" +
                "select " +
                "    s.\"name\" as \"studentName\", " +
                "    g.\"name\" as \"groupName\"" +
                "from test.students s inner join test.\"groups\" g on s.group_id = g.id " +
                "order by s.\"name\"";
        reader.setSql(sql);
        reader.setDataSource(this.dataSource);
        reader.setRowMapper(new FlatFileStudentGroupRowMapper());
        return reader;
    }

    @Bean
    public FlatFileItemWriter<FlatFileStudentGroup> flatFileItemWriterSG() throws Exception {
        FlatFileItemWriter<FlatFileStudentGroup> writer = new FlatFileItemWriter<>();

        //writer.setLineAggregator(new PassThroughLineAggregator<>());
        writer.setLineAggregator(new FlatFileStudentGroupLineAggregator());
        String studentGroupOutputPath = File.createTempFile("studentGroup", "out").getAbsolutePath();
        System.out.println(">> Output path: " + studentGroupOutputPath);
        writer.setResource(new FileSystemResource(studentGroupOutputPath));
        writer.afterPropertiesSet();

        return writer;
    }

    @Bean
    public Step flatFileStepSG() throws Exception {
        return stepBuilderFactory.get("flatFileStepSG")
                .<FlatFileStudentGroup, FlatFileStudentGroup>chunk(10)
                .reader(flatFileItemReaderSG())
                .writer(flatFileItemWriterSG())
                .build();
    }

    @Bean
    public Job flatFileJobSG() throws Exception {
        return jobBuilderFactory.get("flatFileJobSG")
                .start(flatFileStepSG())
                .build();
    }
}
