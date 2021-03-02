package com.yolt.yts.sdk.service.usersite.model.usersite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserSiteRequest {

    private LoginType loginType;

    public CreateUserSiteRequest(LoginType loginType) {
        this.loginType = loginType;
    }

    public CreateUserSiteRequest() { }

    @JsonIgnore
    public boolean isUrlRequest() {
        return this.loginType.equals(LoginType.URL);
    }

    @JsonIgnore
    public boolean isFormRequest() {
        return this.loginType.equals(LoginType.FORM);
    }
}