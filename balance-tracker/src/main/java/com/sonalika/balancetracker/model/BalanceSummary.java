package com.sonalika.balancetracker.model;

public class BalanceSummary {

    private final double netBalance;
    private final double totalCredits;
    private final double totalDebits;
    private final long transactionCount;

    public BalanceSummary(double netBalance,
                          double totalCredits,
                          double totalDebits,
                          long transactionCount) {

        this.netBalance = netBalance;
        this.totalCredits = totalCredits;
        this.totalDebits = totalDebits;
        this.transactionCount = transactionCount;
    }

    public double getNetBalance() {
        return netBalance;
    }

    public double getTotalCredits() {
        return totalCredits;
    }

    public double getTotalDebits() {
        return totalDebits;
    }

    public long getTransactionCount() {
        return transactionCount;
    }
}
