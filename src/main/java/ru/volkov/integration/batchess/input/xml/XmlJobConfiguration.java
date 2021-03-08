package ru.volkov.integration.batchess.input.xml;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.oxm.xstream.XStreamMarshaller;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class XmlJobConfiguration {

    private StepBuilderFactory stepBuilderFactory;
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    public XmlJobConfiguration(
            StepBuilderFactory stepBuilderFactory,
            JobBuilderFactory jobBuilderFactory) {
        this.stepBuilderFactory = stepBuilderFactory;
        this.jobBuilderFactory = jobBuilderFactory;
    }

    @Bean
    public StaxEventItemReader<Customer> staxEventItemReader() {

        XStreamMarshaller unmarshaller = new XStreamMarshaller();

        Map<String, Class> aliases = new HashMap<>();
        aliases.put("customer", Customer.class);

        unmarshaller.setAliases(aliases);

        StaxEventItemReader<Customer> reader = new StaxEventItemReader<>();

        reader.setResource(new ClassPathResource("/temp/customers.xml"));
        reader.setFragmentRootElementName("customer");
        reader.setUnmarshaller(unmarshaller);

        return reader;
    }

    @Bean
    public ItemWriter<Customer> xmlItemWriter() {
        return list -> {
            for(Customer item : list) {
                System.out.println(item.toString());
            }
        };
    }

    @Bean
    public Step xmlStep() {
        return stepBuilderFactory.get("xmlStep")
                .<Customer, Customer> chunk(10)
                .reader(staxEventItemReader())
                .writer(xmlItemWriter())
                .build();
    }

    @Bean
    public Job xmlJob() {
        return jobBuilderFactory.get("xmlJob")
                .start(xmlStep())
                .build();
    }
}
