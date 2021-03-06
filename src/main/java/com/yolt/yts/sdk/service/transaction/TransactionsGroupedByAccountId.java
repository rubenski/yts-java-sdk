package com.yolt.yts.sdk.service.transaction;

import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
public class TransactionsGroupedByAccountId {
    private UUID accountId;
    private Set<String> transactionIds;
}