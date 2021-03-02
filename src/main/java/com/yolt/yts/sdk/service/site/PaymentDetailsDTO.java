package com.yolt.yts.sdk.service.site;

import lombok.Data;

@Data
public class PaymentDetailsDTO {
    boolean supported;
    DynamicFieldsDTO dynamicFields;
}
