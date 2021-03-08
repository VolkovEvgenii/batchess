package ru.volkov.integration.batchess.input.flatfile;

public class Customer {

    private int id;
    private String name;
    private int cardBskNum;

    public Customer() {
    }

    public Customer(int id, String name, int cardBskNum) {
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
}
