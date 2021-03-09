package ru.volkov.integration.batchess.input.multiplefile;

import org.springframework.batch.item.ResourceAware;
import org.springframework.core.io.Resource;

public class MultipleFileCustomer implements ResourceAware {

    private int id;
    private String name;
    private int cardBskNum;
    private Resource resource;

    public MultipleFileCustomer() {
    }

    public MultipleFileCustomer(int id, String name, int cardBskNum) {
        this.id = id;
        this.name = name;
        this.cardBskNum = cardBskNum;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCardBskNum(int cardBskNum) {
        this.cardBskNum = cardBskNum;
    }

    @Override
    public void setResource(Resource resource) {

    }

    @Override
    public String toString() {
        return "MultipleFileCustomer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cardBskNum=" + cardBskNum +
                ", resource=" + resource +
                '}';
    }
}
