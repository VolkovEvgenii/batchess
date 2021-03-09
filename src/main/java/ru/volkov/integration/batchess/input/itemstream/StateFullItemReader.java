package ru.volkov.integration.batchess.input.itemstream;

import org.springframework.batch.item.*;

import java.util.List;

public class StateFullItemReader implements ItemStreamReader<String> {

    private final List<String> items;
    private int cursorIndex = -1;
    private boolean restart = false;

    public StateFullItemReader(List<String> items) {
        this.items = items;
        this.cursorIndex = 0;
    }

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {

        String item = null;

        if(this.cursorIndex < this.items.size()) {
            item = this.items.get(this.cursorIndex);
            this.cursorIndex ++;
        }

        if(this.cursorIndex == 42 && !restart) {
            throw new RuntimeException("Exception!!!");
        }
        return item;
    }

    @Override
    public void open(ExecutionContext executionContext) throws ItemStreamException {

        if(executionContext.containsKey("cursorIndex")) {
            this.cursorIndex = executionContext.getInt("cursorIndex");
            this.restart = true;
        } else {
            this.cursorIndex = 0;
            executionContext.put("cursorIndex", this.cursorIndex);
        }
    }

    @Override
    public void update(ExecutionContext executionContext) throws ItemStreamException {
        executionContext.put("cursorIndex", this.cursorIndex);
    }

    @Override
    public void close() throws ItemStreamException {

    }
}
