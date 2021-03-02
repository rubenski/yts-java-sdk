package com.yolt.yts.sdk;


import com.yolt.yts.sdk.service.accesstoken.AccessToken;
import com.yolt.yts.sdk.service.accesstoken.AccessTokenService;
import lombok.Setter;

@Setter
public abstract class AccessTokenStrategy {

    protected AccessTokenService accessTokenService;

    void setAccessTokenService(AccessTokenService accessTokenService) {
        this.accessTokenService = accessTokenService;
    }

    /**
     * Called whenever the SDK needs a token to mae a call to YTS
     */
    abstract AccessToken getToken();
}
