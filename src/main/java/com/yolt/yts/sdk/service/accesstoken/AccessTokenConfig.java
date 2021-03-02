package com.yolt.yts.sdk.service.accesstoken;

import lombok.NonNull;
import lombok.Value;

@Value
public class AccessTokenConfig {
    @NonNull
    String signingKeyStore;
    @NonNull
    String signingKeyStorePw;
    @NonNull
    String signingKeyAlias;
    @NonNull
    String ytsSignatureVerificationId;
}
