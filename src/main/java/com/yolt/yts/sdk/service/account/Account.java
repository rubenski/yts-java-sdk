package com.yolt.yts.sdk.service.account;

import com.yolt.yts.sdk.service.Currency;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
public class Account {
    private UUID id;
    private String externalId;
    private AccountType type;
    private UserSite userSite;
    private Currency currency;
    private BigDecimal balance;
    private Account.Status status;
    private String name;
    private String product;
    private String accountHolder;
    private AccountReferences accountReferences;
    private Map<String, String> bankSpecific;
    private CreditCardAccount creditCardAccount;
    private CurrentAccount currentAccount;
    private SavingsAccount savingsAccount;
    private BigDecimal interestRate;
    private UsageType usage;
    private String linkedAccount;
    private Instant lastDataFetchTime;
    private List<Balance> balances;

    @Data
    public static class UserSite {
        UUID userSiteId;
        UUID siteId;
    }

    @Data
    static class CurrentAccount {
        String bic;
        BigDecimal creditLimit;
    }

    @Data
    static class CreditCardAccount {
        BigDecimal creditLimit;
        BigDecimal availableCredit;
        String linkedAccount;
    }

    @Data
    static class SavingsAccount {
        String bic;
        String isMoneyPotOf;
    }

    @Data
    static class Balance {
        String currency;
        BigDecimal amount;
        BalanceType type;
    }

    public enum BalanceType {
        CLOSING_BOOKED,
        EXPECTED,
        AUTHORISED,
        OPENING_BOOKED,
        INTERIM_AVAILABLE,
        INTERIM_BOOKED,
        FORWARD_AVAILABLE,
        NON_INVOICED,
        AVAILABLE;
    }

    public enum Status {
        ENABLED, DELETED, BLOCKED;
    }
}