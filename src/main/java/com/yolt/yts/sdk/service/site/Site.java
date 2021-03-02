package com.yolt.yts.sdk.service.site;

import com.yolt.yts.sdk.service.account.AccountType;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class Site {

    private UUID id;
    private String name;
    private List<AccountType> supportedAccountTypes;
    private ConnectionType connectionType;
    private Services services;
    private String groupingBy;
    private List<String> tags;
    private boolean enabled;
    private List<String> availableInCountries;
    private Boolean noLongerSupported;
    private String iconLink;

    @Data
    public static class Services {
        AIS ais;
        PIS pis;

        @Data
        public static class AIS {
            Onboarded onboarded;
            boolean hasRedirectSteps;
            boolean hasFormSteps;
        }

        @Data
        public static class PIS {
            Onboarded onboarded;
            boolean hasRedirectSteps;
            boolean hasFormSteps;
            PaymentDetailsDTO singleSepa;
            PaymentDetailsDTO ukDomesticSingle;
            PeriodicPaymentDetailsDTO periodicSepa;
            PeriodicPaymentDetailsDTO ukDomesticPeriodic;
            PaymentDetailsDTO scheduledSepa;
            PaymentDetailsDTO scheduledUkDomestic;
        }

        @Data
        public static class Onboarded {
            List<UUID> redirectUrlIds;
            boolean client;
        }
    }

    private enum ConnectionType {
        SCRAPER, DIRECT_CONNECTION
    }
}
