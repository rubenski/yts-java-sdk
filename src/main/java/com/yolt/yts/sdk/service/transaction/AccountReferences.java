package com.yolt.yts.sdk.service.transaction;

import lombok.Data;

@Data
public class AccountReferences {
    private String iban;
    private String maskedPan;
    private String pan;
    private String bban;
    private String sortCodeAccountNumber;
}
