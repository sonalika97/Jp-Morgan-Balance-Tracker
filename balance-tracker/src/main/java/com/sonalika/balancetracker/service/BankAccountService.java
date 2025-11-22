package com.sonalika.balancetracker.service;

import com.sonalika.balancetracker.model.Transaction;
import com.sonalika.balancetracker.model.BalanceSummary;

import java.util.List;

public interface BankAccountService {

    void processTransaction(Transaction tx);

    double retrieveBalance();

    double getTotalCredits();

    double getTotalDebits();

    int getTransactionCount();         // number of stored (max 1000)

    long getTotalTransactionCount();   // total processed ever

    BalanceSummary getSummary();

    List<Transaction> getLast1000Transactions();
}
