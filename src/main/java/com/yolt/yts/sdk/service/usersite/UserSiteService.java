package com.yolt.yts.sdk.service.usersite;

import com.yolt.yts.sdk.service.accesstoken.AccessToken;
import com.yolt.yts.sdk.service.usersite.model.login.LoginStep;
import com.yolt.yts.sdk.service.usersite.model.usersite.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static com.yolt.yts.sdk.Constants.*;

public class UserSiteService {

    private final WebClient webClient;

    public UserSiteService(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * Get a bank authentication URL
     * <p>
     * This SDK is intended to support DIRECT_CONNECTION cases, so we don't support calling this endpoint without
     * a redirectUrlId, which would be needed for scraping scenarios. We do support returning forms, because some
     * direct connections require a form, for example for Credit Agricole's region selection.
     *
     * @param token
     * @param userId
     * @param siteId
     * @param redirectUrlId
     * @return
     */
    public LoginStep connect(AccessToken token, UUID userId, String psuIpAddress, UUID siteId, UUID redirectUrlId) {
        return webClient.post()
                .uri(uriBuilder -> buildUri(uriBuilder, userId, siteId, redirectUrlId))
                .header(HEADER_CONTENT_TYPE, "application/json")
                .header(HEADER_AUTHORIZATION, "Bearer " + token.getAccessToken())
                .header(HEADER_PSU_IP_ADDRESS, psuIpAddress)
                .retrieve()
                .bodyToMono(LoginStep.class)
                .block();
    }

    public LoginStep updateConsent(AccessToken token,
                                   UUID userId,
                                   String psuIpAddress,
                                   UUID userSiteId,
                                   UUID redirectUrlId) {
        return webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path(String.format(PATH_UPDATE_CONSENT, userId, userSiteId))
                        .queryParam("redirectUrlId", redirectUrlId.toString())
                        .build())
                .header(HEADER_CONTENT_TYPE, "application/json")
                .header(HEADER_AUTHORIZATION, "Bearer " + token.getAccessToken())
                .header(HEADER_PSU_IP_ADDRESS, psuIpAddress)
                .retrieve()
                .bodyToMono(LoginStep.class)
                .block();
    }

    private URI buildUri(UriBuilder uriBuilder, UUID userId, UUID siteId, UUID redirectUrlId) {
        uriBuilder.path(String.format(PATH_CONNECT_USER, userId.toString()));

        if (redirectUrlId != null) {
            uriBuilder.queryParam("redirectUrlId", redirectUrlId.toString());
        }

        if (siteId != null) {
            uriBuilder.queryParam("site", siteId.toString());
        }

        return uriBuilder.build();
    }

    public CreateUserSiteResponse createUserSite(AccessToken token, UUID userId, String psuIpAddress, String redirectUrl) {
        return webClient.post()
                .uri(String.format(PATH_USER_SITES, userId))
                .body(BodyInserters.fromValue(new UrlCreateUserSiteRequest(redirectUrl)))
                .header(HEADER_CONTENT_TYPE, "application/json")
                .header(HEADER_AUTHORIZATION, "Bearer " + token.getAccessToken())
                .header(HEADER_PSU_IP_ADDRESS, psuIpAddress)
                .retrieve()
                .bodyToMono(CreateUserSiteResponse.class)
                .block();
    }

    public CreateUserSiteResponse createUserSite(AccessToken token, UUID userId, String psuIpAddress, UUID stateId, List<FilledInFormValue> formValues) {
        return webClient.post()
                .uri(String.format(PATH_USER_SITES, userId))
                .body(BodyInserters.fromValue(new FormCreateUserSiteRequest(stateId, formValues)))
                .header(HEADER_CONTENT_TYPE, "application/json")
                .header(HEADER_AUTHORIZATION, "Bearer " + token.getAccessToken())
                .header(HEADER_PSU_IP_ADDRESS, psuIpAddress)
                .retrieve()
                .bodyToMono(CreateUserSiteResponse.class)
                .block();
    }

    public List<UserSite> getUserSites(AccessToken token, UUID userId) {
        return webClient.get()
                .uri(String.format(PATH_USER_SITES, userId))
                .header(HEADER_CONTENT_TYPE, "application/json")
                .header(HEADER_AUTHORIZATION, "Bearer " + token.getAccessToken())
                .retrieve()
                .bodyToFlux(UserSite.class)
                .collectList()
                .block();
    }

    public UserSite getUserSite(AccessToken token, UUID userId, UUID userSiteId) {
        return webClient.get()
                .uri(String.format(PATH_USER_SITE, userId, userSiteId))
                .header(HEADER_CONTENT_TYPE, "application/json")
                .header(HEADER_AUTHORIZATION, "Bearer " + token.getAccessToken())
                .retrieve()
                .bodyToMono(UserSite.class)
                .block();
    }

    public RefreshUserSitesResponse refreshUserSite(AccessToken token, UUID userId, String psuIpAddress, UUID userSiteId) {
        return webClient.put()
                .uri(String.format(PATH_USER_SITE + "/refresh", userId, userSiteId))
                .header(HEADER_CONTENT_TYPE, "application/json")
                .header(HEADER_AUTHORIZATION, "Bearer " + token.getAccessToken())
                .header(HEADER_PSU_IP_ADDRESS, psuIpAddress)
                .retrieve()
                .bodyToMono(RefreshUserSitesResponse.class)
                .block();
    }

    public RefreshUserSitesResponse refreshAllUserSites(AccessToken token, UUID userId, String psuIpAddress) {
        return webClient.put()
                .uri(String.format(PATH_USER_SITES + "/refresh", userId))
                .header(HEADER_CONTENT_TYPE, "application/json")
                .header(HEADER_AUTHORIZATION, "Bearer " + token.getAccessToken())
                .header(HEADER_PSU_IP_ADDRESS, psuIpAddress)
                .retrieve()
                .bodyToMono(RefreshUserSitesResponse.class)
                .block();
    }

    public void deleteUserSite(AccessToken token, UUID userId, String psuIpAddress, UUID userSiteId) {
        webClient.delete()
                .uri(String.format(PATH_USER_SITE, userId, userSiteId))
                .header(HEADER_CONTENT_TYPE, "application/json")
                .header(HEADER_AUTHORIZATION, "Bearer " + token.getAccessToken())
                .header(HEADER_PSU_IP_ADDRESS, psuIpAddress)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

}
