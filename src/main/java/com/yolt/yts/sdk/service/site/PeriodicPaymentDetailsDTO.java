package com.yolt.yts.sdk.service.site;

import lombok.Data;

import java.util.List;

@Data
public class PeriodicPaymentDetailsDTO extends PaymentDetailsDTO {
    private List<String> supportedFrequencies;
}
