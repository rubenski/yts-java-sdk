package com.yolt.yts.sdk.service.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@AllArgsConstructor
public final class ResidentialAddress {
    private final @NonNull String city;
    private final @NonNull String country;
    private final @NonNull String streetName;
    private final @NonNull String houseNumber;
    String houseNumberExtension;
    private final @NonNull String postalCode;
    String province;
}
