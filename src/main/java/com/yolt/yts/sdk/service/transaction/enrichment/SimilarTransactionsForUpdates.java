package com.yolt.yts.sdk.service.transaction.enrichment;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class SimilarTransactionsForUpdates {
    @NonNull
    private UUID updateSessionId;
    private List<SimilarTransactionGroup> groups;
}
