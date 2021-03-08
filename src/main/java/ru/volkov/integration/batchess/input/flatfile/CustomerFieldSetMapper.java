package ru.volkov.integration.batchess.input.flatfile;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class CustomerFieldSetMapper implements FieldSetMapper<Customer> {

    @Override
    public Customer mapFieldSet(FieldSet fieldSet) throws BindException {

        Customer customer = new Customer();
        customer.setId(fieldSet.readInt("id"));
        customer.setName(fieldSet.readString("name"));
        customer.setCardBskNum(fieldSet.readInt("cardBskNum"));
        return customer;
    }
}
