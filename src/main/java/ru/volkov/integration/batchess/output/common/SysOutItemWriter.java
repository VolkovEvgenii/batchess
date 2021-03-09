package ru.volkov.integration.batchess.output.common;

import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class SysOutItemWriter implements ItemWriter<String> {

    @Override
    public void write(List<? extends String> list) throws Exception {
        System.out.println("The size of chunk was: " + list.size());

        for(String item : list) {
            System.out.println(">> " + item);
        }
    }
}
