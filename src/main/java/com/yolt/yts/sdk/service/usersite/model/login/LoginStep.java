package com.yolt.yts.sdk.service.usersite.model.login;

import lombok.Data;
import java.util.UUID;

@Data
public class LoginStep {
    private FormStep form;
    private RedirectStep redirect;
    // This is the reserved user-site id
    private UUID userSiteId;
}
