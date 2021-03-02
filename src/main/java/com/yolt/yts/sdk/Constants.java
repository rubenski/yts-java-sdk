package com.yolt.yts.sdk;

public class Constants {
    public static final String PATH_TOKENS = "/v1/tokens";
    public static final String PATH_SITES = "/v2/sites";
    public static final String PATH_CONNECT_USER = "/v1/users/%s/connect";
    public static final String PATH_UPDATE_CONSENT = "/v1/users/%s/user-sites/%s/renew-access";
    public static final String PATH_USERS = "/v2/users";
    public static final String PATH_USER_SITES = "/v1/users/%s/user-sites";
    public static final String PATH_USER_SITE = "/v1/users/%s/user-sites/%s";
    public static final String PATH_ACCOUNTS = "/v1/users/%s/accounts";
    public static final String PATH_TRANSACTIONS = "/v1/users/%s/transactions";

    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_PSU_IP_ADDRESS = "PSU-IP-Address";

}
