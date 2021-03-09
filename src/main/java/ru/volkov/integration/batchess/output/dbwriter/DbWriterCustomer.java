package ru.volkov.integration.batchess.output.dbwriter;

public class DbWriterCustomer {

    private int id;
    private String name;
    private int cardBskNum;

    public DbWriterCustomer() {
    }

    public DbWriterCustomer(int id, String name, int cardBskNum) {
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

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getCardBskNum() {
        return cardBskNum;
    }

    @Override
    public String toString() {
        return "dbWriterCustomer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cardBskNum=" + cardBskNum +
                '}';
    }
}
