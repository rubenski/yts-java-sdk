package com.yolt.yts.sdk.service.transaction.enrichment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SimilarTransactionsMerchantUpdate {
    private UUID updateSessionId;
    private Set<String> groupSelectors;
    private String newCounterpartyName;
}
