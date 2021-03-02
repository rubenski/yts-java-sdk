package com.yolt.yts.sdk.service.usersite.model.usersite;

import lombok.Data;

import java.util.UUID;

@Data
public class CreateUserSiteResponse {
    private UUID activityId;
    private Step step;
    private UUID userSiteId;
    private UserSite userSite;
}
