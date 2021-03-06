package com.yolt.yts.sdk.service.transaction;

import lombok.Data;
import lombok.NonNull;

import java.util.Set;

@Data
public class SimilarTransactionGroup {
    private String groupSelector;
    @NonNull
    private String groupDescription;
    private int count;
    private Set<TransactionsGroupedByAccountId> transactions;
}
