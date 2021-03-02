package com.yolt.yts.sdk.service.transaction;

import com.yolt.yts.sdk.service.accesstoken.AccessToken;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.yolt.yts.sdk.Constants.*;

public class TransactionService {

    private final WebClient webClient;

    public TransactionService(WebClient webClient) {
        this.webClient = webClient;
    }

    public TransactionPage getTransactions(AccessToken token, UUID userId) {
        return getTransactionsFromYts(token, userId, null, null, null);
    }

    public TransactionPage getTransactions(AccessToken token, UUID userId, Next next) {
        return getTransactionsFromYts(token, userId, null, null, next);
    }

    public TransactionPage getTransactions(AccessToken token, UUID userId, DateInterval dateInterval) {
        return getTransactionsFromYts(token, userId, dateInterval, null, null);
    }

    public TransactionPage getTransactions(AccessToken token, UUID userId, DateInterval dateInterval, Next next) {
        return getTransactionsFromYts(token, userId, dateInterval, null, next);
    }

    public TransactionPage getTransactions(AccessToken token, UUID userId, List<UUID> accountIds) {
        return getTransactionsFromYts(token, userId, null, accountIds, null);
    }

    public TransactionPage getTransactions(AccessToken token, UUID userId, List<UUID> accountIds, Next next) {
        return getTransactionsFromYts(token, userId, null, accountIds, next);
    }

    public TransactionPage getTransactions(AccessToken token, UUID userId, DateInterval dateInterval, List<UUID> accountIds) {
        return getTransactionsFromYts(token, userId, dateInterval, accountIds, null);
    }

    public TransactionPage getTransactions(AccessToken token, UUID userId, DateInterval dateInterval, List<UUID> accountIds, Next next) {
        return getTransactionsFromYts(token, userId, dateInterval, accountIds, next);
    }

    private TransactionPage getTransactionsFromYts(AccessToken token, UUID userId, DateInterval dateInterval, List<UUID> accountIds, Next next) {
        return webClient.get()
                .uri(uriBuilder -> buildUri(uriBuilder, userId, dateInterval, accountIds, next))
                .header(HEADER_CONTENT_TYPE, "application/json")
                .header(HEADER_AUTHORIZATION, "Bearer " + token.getAccessToken())
                .retrieve()
                .bodyToMono(TransactionPage.class)
                .block();
    }

    private URI buildUri(UriBuilder uriBuilder, UUID userId, DateInterval dateInterval, List<UUID> accountIds, Next next) {
        final UriBuilder builder = uriBuilder.path(String.format(PATH_TRANSACTIONS, userId.toString()));

        if (dateInterval != null) {
            builder.queryParam("dateInterval", dateInterval);
        }

        if (accountIds != null && !accountIds.isEmpty()) {
            final String accountIdsString = accountIds.stream().map(UUID::toString).collect(Collectors.joining(","));
            builder.queryParam("accountIds", String.join(",", accountIdsString));
        }

        if (next != null) {
            builder.queryParam("next", next.getNext());
        }

        return builder.build();
    }


}
