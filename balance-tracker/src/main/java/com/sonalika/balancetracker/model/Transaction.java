package com.sonalika.balancetracker.model;

public class Transaction {

    private String id;
    private double amountPounds;

    public Transaction() {
    }

    public Transaction(String id, double amountPounds) {
        this.id = id;
        this.amountPounds = amountPounds;
    }

    public String getId() {
        return id;
    }

    public double getAmountPounds() {
        return amountPounds;
    }

    // Optional: setters for JSON deserialization
    public void setId(String id) {
        this.id = id;
    }

    public void setAmountPounds(double amountPounds) {
        this.amountPounds = amountPounds;
    }
}
