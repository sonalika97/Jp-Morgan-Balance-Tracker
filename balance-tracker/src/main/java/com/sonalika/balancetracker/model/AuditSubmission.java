package com.sonalika.balancetracker.model;

import java.util.List;

public class AuditSubmission {

    private final List<AuditBatch> batches;

    public AuditSubmission(List<AuditBatch> batches) {
        this.batches = batches;
    }

    public List<AuditBatch> getBatches() {
        return batches;
    }
}
