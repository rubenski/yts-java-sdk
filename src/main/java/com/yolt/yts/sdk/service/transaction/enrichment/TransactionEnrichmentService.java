package com.yolt.yts.sdk.service.transaction.enrichment;

import com.yolt.yts.sdk.service.accesstoken.AccessToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static com.yolt.yts.sdk.Constants.*;

@Slf4j
public class TransactionEnrichmentService {

    private final WebClient webClient;
    private final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public TransactionEnrichmentService(WebClient webClient) {
        this.webClient = webClient;
    }

    public SimilarTransactionsForUpdates getSimilarTransactions(AccessToken token, UUID userId, UUID accountId, UUID transactionId, LocalDate date) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.path(String.format(PATH_SIMILAR_TRANSACTIONS, userId.toString()))
                        .queryParam("accountId", accountId.toString())
                        .queryParam("transactionId", transactionId.toString())
                        .queryParam("date", date.format(FORMATTER))
                        .build())
                .header(HEADER_CONTENT_TYPE, "application/json")
                .header(HEADER_AUTHORIZATION, "Bearer " + token.getAccessToken())
                .retrieve()
                .bodyToMono(SimilarTransactionsForUpdates.class)
                .block();
    }

    public MerchantSuggestions getMerchantSuggestions(AccessToken token, UUID userId) {
        return webClient.get()
                .uri(uriBuilder -> {
                    return uriBuilder.path(String.format(PATH_MERCHANT_SUGGESTIONS, userId.toString())).build();
                })
                .header(HEADER_CONTENT_TYPE, "application/json")
                .header(HEADER_AUTHORIZATION, "Bearer " + token.getAccessToken())
                .retrieve()
                .bodyToMono(MerchantSuggestions.class)
                .block();
    }

    public EnrichmentUpdateActivity updateMerchantForTransaction(AccessToken token, UUID userId, SingleTransactionMerchantUpdate merchantUpdate) {
        return webClient.patch()
                .uri(uriBuilder -> {
                    return uriBuilder.path(String.format(PATH_MERCHANT_UPDATE_SINGLE, userId.toString())).build();
                })
                .body(BodyInserters.fromValue(merchantUpdate))
                .header(HEADER_CONTENT_TYPE, "application/json")
                .header(HEADER_AUTHORIZATION, "Bearer " + token.getAccessToken())
                .retrieve()
                .bodyToMono(EnrichmentUpdateActivity.class)
                .block();
    }

    public EnrichmentUpdateActivity updateMerchantForSimilarTransactions(AccessToken token, UUID userId, SimilarTransactionsMerchantUpdate merchantUpdate) {
        return webClient.post()
                .uri(uriBuilder -> {
                    return uriBuilder.path(String.format(PATH_MERCHANT_UPDATE_SIMILAR, userId.toString())).build();
                })
                .body(BodyInserters.fromValue(merchantUpdate))
                .header(HEADER_CONTENT_TYPE, "application/json")
                .header(HEADER_AUTHORIZATION, "Bearer " + token.getAccessToken())
                .retrieve()
                .bodyToMono(EnrichmentUpdateActivity.class)
                .block();
    }


}
