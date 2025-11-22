package com.sonalika.balancetracker.service;

import com.sonalika.balancetracker.model.AuditSubmission;
import com.sonalika.balancetracker.model.Transaction;

import java.util.List;

public interface AuditService {

    
    AuditSubmission buildSubmission(List<Transaction> transactions);
}
