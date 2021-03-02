package com.yolt.yts.sdk.http;

import lombok.Getter;
import lombok.NonNull;

@Getter
public class HttpClientConfig {

    private int defaultPoolSize;
    private int maxPoolSize;
    private int connectTimeOutMs;
    private int socketTimeOutMs;
    private String keyStoreClassPath;
    private String keyStorePw;
    private String trustStoreClassPath;
    private String trustStorePw;
    private String baseUrl;
    private boolean logRequestsResponses;

    public boolean isMtls() {
        return keyStoreClassPath != null;
    }

    private HttpClientConfig() {
    }

    public static class Builder {
        private int defaultPoolSize = 2;
        private int maxPoolSize = 10;
        private int connectTimeOutMs = 2000;
        private int socketTimeOutMs = 4000;
        private String keyStoreClassPath;
        private String keyStorePw;
        private String trustStoreClassPath;
        private String trustStorePw;
        private final String baseUrl;
        private boolean logRequestsResponses;

        public Builder(@NonNull String baseUrl) {
            this.baseUrl = baseUrl;
        }

        public Builder setKeyStore(@NonNull String keyStoreClassPath, String keyStorePw) {
            this.keyStoreClassPath = keyStoreClassPath;
            this.keyStorePw = keyStorePw;
            return this;
        }

        public Builder setTrustStore(@NonNull String trustStoreClassPath, String trustStorePw) {
            this.trustStoreClassPath = trustStoreClassPath;
            this.trustStorePw = trustStorePw;
            return this;
        }

        public Builder setTimeouts(int connectTimeoutMs, int socketTimeOutMs) {
            this.connectTimeOutMs = connectTimeoutMs;
            this.socketTimeOutMs = socketTimeOutMs;
            return this;
        }

        public Builder setPoolConfig(int defaultPoolSize, int maxPoolSize) {
            this.defaultPoolSize = defaultPoolSize;
            this.maxPoolSize = maxPoolSize;
            return this;
        }

        public Builder setLogRequestsResponses(boolean log) {
            this.logRequestsResponses = log;
            return this;
        }

        public HttpClientConfig build() {
            HttpClientConfig config = new HttpClientConfig();
            config.baseUrl = baseUrl;
            config.connectTimeOutMs = connectTimeOutMs;
            config.defaultPoolSize = defaultPoolSize;
            config.keyStoreClassPath = keyStoreClassPath;
            config.keyStorePw = keyStorePw;
            config.maxPoolSize = maxPoolSize;
            config.socketTimeOutMs = socketTimeOutMs;
            config.trustStoreClassPath = trustStoreClassPath;
            config.trustStorePw = trustStorePw;
            config.logRequestsResponses = logRequestsResponses;
            return config;
        }
    }
}
