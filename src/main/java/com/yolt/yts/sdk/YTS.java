package com.yolt.yts.sdk;


import com.yolt.yts.sdk.http.HttpClientConfig;
import com.yolt.yts.sdk.http.WebClientCreator;
import com.yolt.yts.sdk.service.accesstoken.AccessToken;
import com.yolt.yts.sdk.service.accesstoken.AccessTokenConfig;
import com.yolt.yts.sdk.service.accesstoken.AccessTokenService;
import com.yolt.yts.sdk.service.account.Account;
import com.yolt.yts.sdk.service.account.AccountService;
import com.yolt.yts.sdk.service.site.Site;
import com.yolt.yts.sdk.service.site.SiteService;
import com.yolt.yts.sdk.service.transaction.*;
import com.yolt.yts.sdk.service.user.KycDetails;
import com.yolt.yts.sdk.service.user.User;
import com.yolt.yts.sdk.service.user.UserService;
import com.yolt.yts.sdk.service.usersite.UserSiteService;
import com.yolt.yts.sdk.service.usersite.model.login.LoginStep;
import com.yolt.yts.sdk.service.usersite.model.usersite.CreateUserSiteResponse;
import com.yolt.yts.sdk.service.usersite.model.usersite.FilledInFormValue;
import com.yolt.yts.sdk.service.usersite.model.usersite.RefreshUserSitesResponse;
import com.yolt.yts.sdk.service.usersite.model.usersite.UserSite;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.UUID;

@Slf4j
public class YTS {

    // Services
    private final AccessTokenService accessTokenService;
    private final SiteService siteService;
    private final UserSiteService userSiteService;
    private final UserService userService;
    private final AccountService accountService;
    private final TransactionService transactionService;

    private final AccessTokenStrategy accessTokenStrategy;


    public YTS(@NonNull UUID clientId,
               @NonNull AccessTokenConfig accessTokenConfig,
               @NonNull HttpClientConfig httpClientConfig) {
        var webClient = WebClientCreator.createWithJetty(httpClientConfig);
        this.accessTokenService = new AccessTokenService(webClient, accessTokenConfig, clientId);
        this.siteService = new SiteService(webClient);
        this.userSiteService = new UserSiteService(webClient);
        this.userService = new UserService(webClient);
        this.accountService = new AccountService(webClient);
        this.transactionService = new TransactionService(webClient);

        final SlightlyLazyAccessTokenStrategy slightlyLazyAccessTokenStrategy = new SlightlyLazyAccessTokenStrategy();
        slightlyLazyAccessTokenStrategy.setAccessTokenService(accessTokenService);
        this.accessTokenStrategy = slightlyLazyAccessTokenStrategy;
    }

    public YTS(@NonNull UUID clientId,
               @NonNull AccessTokenConfig accessTokenConfig,
               @NonNull HttpClientConfig httpClientConfig,
               @NonNull AccessTokenStrategy accessTokenStrategy) {
        var webClient = WebClientCreator.createWithJetty(httpClientConfig);
        this.accessTokenService = new AccessTokenService(webClient, accessTokenConfig, clientId);
        this.siteService = new SiteService(webClient);
        this.userSiteService = new UserSiteService(webClient);
        this.userService = new UserService(webClient);
        this.accountService = new AccountService(webClient);
        this.transactionService = new TransactionService(webClient);

        this.accessTokenStrategy = accessTokenStrategy;
    }

    private AccessToken getToken() {
        return this.accessTokenStrategy.getToken();
    }

    public byte[] getSiteLogo(@NonNull String path) {
        return this.siteService.getSiteLogo(getToken(), path);
    }

    public List<Site> getSites() {
        return this.siteService.getSites(getToken(), null, null);
    }

    public List<Site> getSites(@NonNull List<String> tags) {
        return this.siteService.getSites(getToken(), tags, null);
    }

    public List<Site> getSites(@NonNull UUID redirectUrlId) {
        return this.siteService.getSites(getToken(), null, redirectUrlId);
    }

    public List<Site> getSites(@NonNull List<String> tags, @NonNull UUID redirectUrlId) {
        return this.siteService.getSites(getToken(), tags, redirectUrlId);
    }

    public Site getSite(@NonNull UUID siteId) {
        return this.siteService.getSite(getToken(), siteId);
    }

    public User createUser() {
        return this.userService.createUser(getToken());
    }

    public User createUser(@NonNull KycDetails kycDetails) {
        return this.userService.createUser(getToken(), kycDetails);
    }

    public LoginStep connectToSite(@NonNull UUID userId,
                                   @NonNull String psuIpAddress,
                                   @NonNull UUID siteId,
                                   @NonNull UUID redirectUrlId) {
        return userSiteService.connect(getToken(), userId, psuIpAddress, siteId, redirectUrlId);
    }

    public LoginStep updateConsent(@NonNull UUID userId,
                                   @NonNull String psuIpAddress,
                                   @NonNull UUID userSiteId,
                                   @NonNull UUID redirectUrlId) {
        return userSiteService.updateConsent(getToken(), userId, psuIpAddress, userSiteId, redirectUrlId);
    }

    public CreateUserSiteResponse createUserSite(@NonNull UUID userId,
                                                 @NonNull String psuIpAddress,
                                                 @NonNull String redirectUrl) {
        return userSiteService.createUserSite(getToken(), userId, psuIpAddress, redirectUrl);
    }

    public CreateUserSiteResponse createUserSite(@NonNull UUID userId,
                                                 @NonNull String psuIpAddress,
                                                 @NonNull UUID stateId,
                                                 @NonNull List<FilledInFormValue> filledInFormValues) {
        return userSiteService.createUserSite(getToken(), userId, psuIpAddress, stateId, filledInFormValues);
    }

    public List<UserSite> getUserSites(@NonNull UUID userId) {
        return userSiteService.getUserSites(getToken(), userId);
    }

    public UserSite getUserSite(@NonNull UUID userId, @NonNull UUID userSiteId) {
        return userSiteService.getUserSite(getToken(), userId, userSiteId);
    }

    public RefreshUserSitesResponse refreshUserSite(@NonNull UUID userId,
                                                    @NonNull String psuIpAddress,
                                                    @NonNull UUID userSiteId) {
        return userSiteService.refreshUserSite(getToken(), userId, psuIpAddress, userSiteId);
    }

    public RefreshUserSitesResponse refreshAllUserSites(@NonNull UUID userId,
                                                        @NonNull String psuIpAddress) {
        return userSiteService.refreshAllUserSites(getToken(), userId, psuIpAddress);
    }

    public void deleteUserSite(@NonNull UUID userId,
                               @NonNull String psuIpAddress,
                               @NonNull UUID userSiteId) {
        userSiteService.deleteUserSite(getToken(), userId, psuIpAddress, userSiteId);
    }

    public List<Account> getAccounts(@NonNull UUID userId) {
        return accountService.getAccounts(getToken(), userId);
    }

    public List<Account> getAccounts(@NonNull UUID userId, @NonNull UUID userSiteId) {
        return accountService.getAccounts(getToken(), userId, userSiteId);
    }

    public TransactionPage getTransactions(@NonNull UUID userId) {
        return transactionService.getTransactions(getToken(), userId);
    }

    public TransactionPage getTransactions(@NonNull UUID userId, @NonNull Next next) {
        return transactionService.getTransactions(getToken(), userId, next);
    }

    public TransactionPage getTransactions(@NonNull UUID userId, @NonNull DateInterval dateInterval) {
        return transactionService.getTransactions(getToken(), userId, dateInterval);
    }

    public TransactionPage getTransactions(@NonNull UUID userId, @NonNull DateInterval dateInterval, @NonNull Next next) {
        return transactionService.getTransactions(getToken(), userId, dateInterval, next);
    }

    public TransactionPage getTransactions(@NonNull UUID userId, @NonNull List<UUID> accountIds) {
        return transactionService.getTransactions(getToken(), userId, accountIds);
    }

    public TransactionPage getTransactions(@NonNull UUID userId, @NonNull List<UUID> accountIds, @NonNull Next next) {
        return transactionService.getTransactions(getToken(), userId, accountIds, next);
    }

    public TransactionPage getTransactions(@NonNull UUID userId, @NonNull DateInterval dateInterval, @NonNull List<UUID> accountIds) {
        return transactionService.getTransactions(getToken(), userId, dateInterval, accountIds);
    }

    public TransactionPage getTransactions(@NonNull UUID userId, @NonNull DateInterval dateInterval, @NonNull List<UUID> accountIds, @NonNull Next next) {
        return transactionService.getTransactions(getToken(), userId, dateInterval, accountIds, next);
    }
}
