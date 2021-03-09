package ru.volkov.integration.batchess.output.flatfile;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.item.file.transform.LineAggregator;

public class FlatFileStudentGroupLineAggregator implements LineAggregator<FlatFileStudentGroup> {

    private ObjectMapper mapper = new ObjectMapper();

    @Override
    public String aggregate(FlatFileStudentGroup flatFileStudentGroup) {
        try {
            return mapper.writeValueAsString(flatFileStudentGroup);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Unable to serialize studentGroup", e);
        }
    };
}
