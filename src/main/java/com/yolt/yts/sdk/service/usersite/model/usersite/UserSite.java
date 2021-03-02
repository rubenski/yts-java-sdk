package com.yolt.yts.sdk.service.usersite.model.usersite;

import com.yolt.yts.sdk.service.account.AccountType;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
public class UserSite {

    private UUID id;
    private ConnectionStatus connectionStatus;
    private FailureReason lastDataFetchFailureReason;
    private Site site;
    private Instant lastDataFetchTime;
    private Instant consentValidFrom;
    private Map<String, String> metaData;

    @Data
    public static class Site {
        UUID id;
        String name;
        List<AccountType> supportedAccountTypes;
    }
}
