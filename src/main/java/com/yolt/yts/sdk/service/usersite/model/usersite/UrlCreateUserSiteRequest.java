package com.yolt.yts.sdk.service.usersite.model.usersite;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UrlCreateUserSiteRequest  extends CreateUserSiteRequest {

    private String redirectUrl;

    public UrlCreateUserSiteRequest(String redirectUrl) {
        super(LoginType.URL);
        this.redirectUrl = redirectUrl;
    }

    public UrlCreateUserSiteRequest() {
        super(LoginType.URL);
    }
}
