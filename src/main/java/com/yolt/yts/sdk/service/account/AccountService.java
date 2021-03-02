package com.yolt.yts.sdk.service.account;

import com.yolt.yts.sdk.service.accesstoken.AccessToken;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static com.yolt.yts.sdk.Constants.*;

public class AccountService {

    private final WebClient webClient;

    public AccountService(WebClient webClient) {
        this.webClient = webClient;
    }

    public List<Account> getAccounts(AccessToken token, UUID userId) {
        return getAccountsFromYts(token, userId, null);
    }

    public List<Account> getAccounts(AccessToken token, UUID userId, UUID userSiteId) {
        return getAccountsFromYts(token, userId, userSiteId);
    }

    private List<Account> getAccountsFromYts(AccessToken token, UUID userId, UUID userSiteId) {
        return webClient.get()
                .uri(uriBuilder -> buildUri(uriBuilder, userId, userSiteId))
                .header(HEADER_CONTENT_TYPE, "application/json")
                .header(HEADER_AUTHORIZATION, "Bearer " + token.getAccessToken())
                .retrieve()
                .bodyToFlux(Account.class)
                .collectList()
                .block();
    }

    private URI buildUri(UriBuilder uriBuilder, UUID userId, UUID userSiteId) {
        final UriBuilder builder = uriBuilder.path(String.format(PATH_ACCOUNTS, userId));
        if (userSiteId != null) {
            builder.queryParam("userSiteId", userSiteId.toString());
        }
        return builder.build();
    }
}
