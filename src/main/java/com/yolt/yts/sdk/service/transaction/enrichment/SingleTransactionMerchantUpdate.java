package com.yolt.yts.sdk.service.transaction.enrichment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleTransactionMerchantUpdate {
    private UUID accountId;
    private String id;
    private LocalDate date;
    private String counterpartyName;
}