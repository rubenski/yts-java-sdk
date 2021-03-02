package com.yolt.yts.sdk.service.transaction;

import com.yolt.yts.sdk.service.Currency;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

@Data
public class Transaction {
    private String id;
    private String externalId;
    private UUID accountId;
    private TransactionStatus status;
    private LocalDate date;
    private ZonedDateTime timestamp;
    private LocalDate bookingDate;
    private LocalDate valueDate;
    private BigDecimal amount;
    private Currency currency;
    private String description;
    private String endToEndId;
    private Creditor creditor;
    private Debtor debtor;
    private String bankTransactionCode;
    private String purposeCode;
    private ExchangeRate exchangeRate;
    private OriginalAmount originalAmount;
    private Enrichment enrichment;
    private Instant lastUpdatedTime;
    private Map<String, String> bankSpecific;
    private String remittanceInformationStructured;

    public enum TransactionStatus {
        PENDING,
        BOOKED,
        HOLD;
    }

    @Data
    public static class OriginalAmount {
        private BigDecimal amount;
        private Currency currency;
    }

    @Data
    public static class Creditor {
        private String name;
        private AccountReferences accountReferences;

    }

    @Data
    public static class Debtor {
        private String name;
        private AccountReferences accountReferences;
    }

    @Data
    public static class Enrichment {
        private String category;
        private Merchant merchant;
        private TransactionCycle cycle;
        private Set<String> labels;

        public static class Merchant {
            public String name;
        }
    }

    @Data
    public static class ExchangeRate {
        private Currency currencyFrom;
        private Currency currencyTo;
        private BigDecimal rate;
    }
}
