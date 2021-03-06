package com.yolt.yts.sdk.service.transaction;

import lombok.Data;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

@Data
public class SimilarTransactionsForUpdates {
    @NonNull
    private UUID updateSessionId;
    private List<SimilarTransactionGroup> groups;
}
