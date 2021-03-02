package com.yolt.yts.sdk.service.account;
import lombok.Data;

@Data
public class AccountReferences {
    String iban;
    String maskedPan;
    String pan;
    String bban;
    String sortCodeAccountNumber;
}