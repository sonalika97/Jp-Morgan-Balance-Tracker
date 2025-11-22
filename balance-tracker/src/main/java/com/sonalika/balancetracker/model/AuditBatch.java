package com.sonalika.balancetracker.model;

import java.util.List;

public class AuditBatch {

    private final double totalValueOfAllTransactions;
    private final int countOfTransactions;
    private final List<Transaction> transactions;

    public AuditBatch(double totalValueOfAllTransactions,
                      int countOfTransactions,
                      List<Transaction> transactions) {

        this.totalValueOfAllTransactions = totalValueOfAllTransactions;
        this.countOfTransactions = countOfTransactions;
        this.transactions = transactions;
    }

    public double getTotalValueOfAllTransactions() {
        return totalValueOfAllTransactions;
    }

    public int getCountOfTransactions() {
        return countOfTransactions;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
