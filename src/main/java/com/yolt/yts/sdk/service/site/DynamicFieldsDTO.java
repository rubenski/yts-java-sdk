package com.yolt.yts.sdk.service.site;

import lombok.Data;

@Data
public class DynamicFieldsDTO {

    private DynamicFieldDTO creditorAgentBic;
    private DynamicFieldDTO creditorAgentName;
    private DynamicFieldDTO remittanceInformationStructured;
    private DynamicFieldDTO creditorPostalAddressLine;
    private DynamicFieldDTO creditorPostalCountry;
    private DynamicFieldDTO debtorName;

    @Data
    public static class DynamicFieldDTO {
        private boolean required;
    }
}
