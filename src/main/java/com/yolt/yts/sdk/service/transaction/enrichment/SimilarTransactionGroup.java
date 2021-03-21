package com.yolt.yts.sdk.service.transaction.enrichment;

import com.yolt.yts.sdk.service.transaction.TransactionsGroupedByAccountId;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.Set;

@Data
@NoArgsConstructor
public class SimilarTransactionGroup {
    private String groupSelector;
    @NonNull
    private String groupDescription;
    private int count;
    private Set<TransactionsGroupedByAccountId> transactions;
}
