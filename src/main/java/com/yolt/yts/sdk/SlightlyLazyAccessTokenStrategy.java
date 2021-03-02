package com.yolt.yts.sdk;

import com.yolt.yts.sdk.service.accesstoken.AccessToken;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 *
 */
@Slf4j
public class SlightlyLazyAccessTokenStrategy extends AccessTokenStrategy {

    // Token
    private static AccessToken accessToken;
    private static LocalDateTime expires;

    @Override
    AccessToken getToken() {
        if (accessToken == null || LocalDateTime.now().plus(1, ChronoUnit.MINUTES).isAfter(expires)) {
            if (accessToken != null) {
                log.debug("Access expires in {} seconds. Fetching new one.", ChronoUnit.SECONDS.between(LocalDateTime.now(), expires));
            } else {
                log.debug("Getting first access token");
            }
            accessToken = accessTokenService.getToken();
            expires = LocalDateTime.now().plus(accessToken.getExpiresIn(), ChronoUnit.SECONDS);
        }
        return accessToken;
    }
}
