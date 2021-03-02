package com.yolt.yts.sdk.service.user;

import lombok.Value;

import java.time.LocalDate;

@Value
public class KycDetails {
    LocalDate dateOfBirth;
    Name name;
    ResidentialAddress residentialAddress;
}
