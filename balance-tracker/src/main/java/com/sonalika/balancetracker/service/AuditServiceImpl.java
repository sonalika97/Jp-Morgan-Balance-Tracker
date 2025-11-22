package com.sonalika.balancetracker.service;

import com.sonalika.balancetracker.model.AuditBatch;
import com.sonalika.balancetracker.model.AuditSubmission;
import com.sonalika.balancetracker.model.Transaction;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class AuditServiceImpl implements AuditService {

    private static final double BATCH_LIMIT = 1_000_000.0;

    @Override
    public AuditSubmission buildSubmission(List<Transaction> transactions) {

        if (transactions == null || transactions.isEmpty()) {
            return new AuditSubmission(Collections.emptyList());
        }

        List<Transaction> sorted = new ArrayList<>(transactions);
        sorted.sort(Comparator.comparingDouble(t -> -Math.abs(t.getAmountPounds())));

       
        List<BatchBuilder> batchBuilders = new ArrayList<>();

        for (Transaction tx : sorted) {
            double value = Math.abs(tx.getAmountPounds());
            boolean placed = false;

            
            for (BatchBuilder builder : batchBuilders) {
                if (builder.canAdd(value)) {
                    builder.add(tx, value);
                    placed = true;
                    break;
                }
            }

           
            if (!placed) {
                BatchBuilder newBuilder = new BatchBuilder();
                newBuilder.add(tx, value);
                batchBuilders.add(newBuilder);
            }
        }

    
        List<AuditBatch> batches = new ArrayList<>();
        for (BatchBuilder builder : batchBuilders) {
            batches.add(builder.build());
        }

        AuditSubmission submission = new AuditSubmission(batches);

       
        logSubmission(submission);

        return submission;
    }

   
    private static class BatchBuilder {
        private double totalValue = 0.0;
        private final List<Transaction> transactions = new ArrayList<>();

        boolean canAdd(double value) {
            return totalValue + value <= BATCH_LIMIT;
        }

        void add(Transaction tx, double value) {
            transactions.add(tx);
            totalValue += value;
        }

        AuditBatch build() {
            return new AuditBatch(
                    totalValue,
                    transactions.size(),
                    Collections.unmodifiableList(new ArrayList<>(transactions)) // Java 8+ safe
            );
        }
    }

  
    private void logSubmission(AuditSubmission submission) {
        System.out.println("{");
        System.out.println("  submission: {");
        System.out.println("    batches: [");

        List<AuditBatch> batches = submission.getBatches();
        for (int i = 0; i < batches.size(); i++) {
            AuditBatch b = batches.get(i);
            System.out.println("      {");
            System.out.println("        totalValueOfAllTransactions: " + b.getTotalValueOfAllTransactions() + ",");
            System.out.println("        countOfTransactions: " + b.getCountOfTransactions());
            System.out.println("      }" + (i < batches.size() - 1 ? "," : ""));
        }

        System.out.println("    ]");
        System.out.println("  }");
        System.out.println("}");
    }
}
