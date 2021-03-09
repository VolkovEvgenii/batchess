package ru.volkov.integration.batchess.input.multiplefile;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class MultipleFileCustomerFielSetMapper implements FieldSetMapper<MultipleFileCustomer> {

    @Override
    public MultipleFileCustomer mapFieldSet(FieldSet fieldSet) throws BindException {

        MultipleFileCustomer customer = new MultipleFileCustomer();
        customer.setId(fieldSet.readInt("id"));
        customer.setName(fieldSet.readString("name"));
        customer.setCardBskNum(fieldSet.readInt("cardBskNum"));
        return customer;
    }

}
