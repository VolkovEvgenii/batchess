package ru.volkov.integration.batchess.input.fromdb;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.support.PostgresPagingQueryProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class FromDbJobConfiguration {

    public JobBuilderFactory jobBuilderFactory;
    public StepBuilderFactory stepBuilderFactory;
    public DataSource dataSource;

    @Autowired
    public FromDbJobConfiguration(
            JobBuilderFactory jobBuilderFactory,
            StepBuilderFactory stepBuilderFactory,
            DataSource dataSource) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
        this.dataSource = dataSource;
    }

    @Bean
    public JdbcCursorItemReader<Student> cursorItemReader(){
        String sql = "" +
                "select " +
                "    s.\"name\" as \"studentName\", " +
                "    g.\"name\" as \"groupName\"" +
                "from test.students s inner join test.\"groups\" g on s.group_id = g.id " +
                "order by s.\"name\"";

        JdbcCursorItemReader<Student> reader = new JdbcCursorItemReader<>();
        reader.setSql(sql);
        reader.setDataSource(this.dataSource);
        reader.setRowMapper(new StudentRowMapper());

        return reader;
    }

/*    @Bean
    public JdbcPagingItemReader<Student> jdbcPagingItemReader(){
        JdbcPagingItemReader<Student> reader = new JdbcPagingItemReader<>();

        reader.setDataSource(this.dataSource);
        reader.setFetchSize(10);
        reader.setRowMapper(new StudentRowMapper());

        PostgresPagingQueryProvider queryProvider = new PostgresPagingQueryProvider();
        queryProvider.setSelectClause("s.name as student_name, g.name as group_name");
        queryProvider.setFromClause("test.students as s inner join test.groups as g on s.group_id = g.id ");
        Map<String, Order> sortKeys = new HashMap<>(1);

        sortKeys.put("s.name", Order.ASCENDING);

        queryProvider.setSortKeys(sortKeys);
        reader.setQueryProvider(queryProvider);

        return reader;
    }*/

    @Bean
    public ItemWriter<Student> itemWriter(){
        return list -> {
            for (Student student : list) {
                System.out.println(student.toString());
            }
        };
    }

    @Bean
    public Step fromDbStep1(){
        return stepBuilderFactory.get("fromDbStep1")
                .<Student, Student>chunk(10)
                .reader(cursorItemReader())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public Job fromDbJob(){
        return jobBuilderFactory.get("fromDbJob")
                .start(fromDbStep1())
                .build();
    }


}
