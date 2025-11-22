package com.sonalika.balancetracker.service;

import com.sonalika.balancetracker.model.BalanceSummary;
import com.sonalika.balancetracker.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BankAccountServiceImpl implements BankAccountService {

    private double balance = 0.0;
    private double totalCredits = 0.0;
    private double totalDebits = 0.0;

   
    private final List<Transaction> transactions = new ArrayList<>();

    // stores lifetime count (never resets)
    private long totalTransactionCount = 0;

    private static final int LIMIT = 1000;

    @Override
    public synchronized void processTransaction(Transaction txn) {

        totalTransactionCount++; 
        double amt = txn.getAmountPounds();
        balance += amt;

        if (amt > 0) {
            totalCredits += amt;
        } else {
            totalDebits += amt;
        }

        if (transactions.size() == LIMIT) {
            transactions.remove(0); 
        }
        transactions.add(txn);
    }

    @Override
    public synchronized double retrieveBalance() {
        return balance;
    }

    @Override
    public synchronized double getTotalCredits() {
        return totalCredits;
    }

    @Override
    public synchronized double getTotalDebits() {
        return totalDebits;
    }

    @Override
    public synchronized int getTransactionCount() {
        return transactions.size();  
    }

    @Override
    public synchronized long getTotalTransactionCount() {
        return totalTransactionCount; 
    }

    @Override
    public synchronized BalanceSummary getSummary() {
        return new BalanceSummary(
                balance,
                totalCredits,
                totalDebits,
                totalTransactionCount
        );
    }

    @Override
    public synchronized List<Transaction> getLast1000Transactions() {
        return Collections.unmodifiableList(transactions);
    }
}
