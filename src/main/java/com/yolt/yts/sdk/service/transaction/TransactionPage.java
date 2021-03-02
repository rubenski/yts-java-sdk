package com.yolt.yts.sdk.service.transaction;

import lombok.Data;

import java.util.List;

@Data
public class TransactionPage {
    private List<Transaction> transactions;
    private String next;
}
