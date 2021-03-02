package com.yolt.yts.sdk.service.transaction;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class TransactionCycle {
    private CycleType cycleType;
    private BigDecimal amount;
    private String currency;
    private String period;
    private Set<String> predictedOccurrences;
    private String label;
    boolean subscription;
    private String counterparty;

    public enum CycleType {
        CREDITS,
        DEBITS;
    }
}
