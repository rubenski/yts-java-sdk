package com.yolt.yts.sdk;

import com.yolt.yts.sdk.service.accesstoken.AccessToken;

public class VeryLazyAccessTokenStrategy extends AccessTokenStrategy {

    @Override
    AccessToken getToken() {
        return accessTokenService.getToken();
    }
}
