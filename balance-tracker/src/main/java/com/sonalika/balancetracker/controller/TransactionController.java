package com.sonalika.balancetracker.controller;

import com.sonalika.balancetracker.model.AuditSubmission;
import com.sonalika.balancetracker.model.Transaction;
import com.sonalika.balancetracker.service.AuditService;
import com.sonalika.balancetracker.service.BankAccountService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class TransactionController {

    private final BankAccountService service;
    private final AuditService auditService;

    public TransactionController(BankAccountService service,
                                 AuditService auditService) {
        this.service = service;
        this.auditService = auditService;
    }


    @PostMapping("/transaction")
    public String process(@RequestBody Transaction transaction) {
        service.processTransaction(transaction);
        return "OK";
    }

    
    @GetMapping("/balance")
    public Map<String, Object> fullBalance() {

        Map<String, Object> response = new HashMap<>();
        response.put("netBalance", service.retrieveBalance());
        response.put("totalCredits", service.getTotalCredits());
        response.put("totalDebits", service.getTotalDebits());
        response.put("totalTransactionsProcessed", service.getTotalTransactionCount());
        response.put("last1000Stored", service.getTransactionCount());

        return response;
    }

   
    @GetMapping("/audit")
    public List<Transaction> getAuditBatch() {
        return service.getLast1000Transactions();
    }

    
    @GetMapping("/audit/submit")
    public AuditSubmission submitAudit() {
        List<Transaction> last1000 = service.getLast1000Transactions();
        return auditService.buildSubmission(last1000);
    }
}
