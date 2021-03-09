package ru.volkov.integration.batchess.output.dbwriter;

import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class DbWriterCustomerFieldSetMapper implements FieldSetMapper<DbWriterCustomer> {

    @Override
    public DbWriterCustomer mapFieldSet(FieldSet fieldSet) throws BindException {

        DbWriterCustomer customer = new DbWriterCustomer();
        customer.setId(fieldSet.readInt("id"));
        customer.setName(fieldSet.readString("name"));
        customer.setCardBskNum(fieldSet.readInt("cardBskNum"));
        return customer;
    }
}
