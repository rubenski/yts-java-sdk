package com.yolt.yts.sdk.service.accesstoken;

import com.yolt.yts.sdk.Constants;
import com.yolt.yts.sdk.PfxLoader;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.NumericDate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.security.Key;
import java.security.KeyStore;
import java.util.UUID;

import static com.yolt.yts.sdk.Constants.HEADER_CONTENT_TYPE;

@Slf4j
public class AccessTokenService {

    private final Key signingKey;
    private final String ytsVerificationKeyId;
    private final UUID clientId;
    private final WebClient webClient;

    @SneakyThrows
    public AccessTokenService(@NonNull WebClient webClient,
                              @NonNull AccessTokenConfig accessTokenConfig,
                              @NonNull UUID clientId) {
        final KeyStore keyStore = PfxLoader.load(accessTokenConfig.getSigningKeyStore(), accessTokenConfig.getSigningKeyStorePw());
        if (!keyStore.containsAlias(accessTokenConfig.getSigningKeyAlias())) {
            throw new RuntimeException("Keystore " + accessTokenConfig.getSigningKeyStore() + " doesn't contain a signing key with alias " + accessTokenConfig.getSigningKeyAlias());
        }
        this.signingKey = keyStore.getKey(accessTokenConfig.getSigningKeyAlias(), accessTokenConfig.getSigningKeyStorePw().toCharArray());
        this.ytsVerificationKeyId = accessTokenConfig.getYtsSignatureVerificationId();
        this.clientId = clientId;
        this.webClient = webClient;
    }

    public AccessToken getToken() {
        log.debug("Refreshing YTS access tokenss");
        return webClient.post().uri(Constants.PATH_TOKENS).body(BodyInserters
                .fromFormData("request_token", createRequestToken())
                .with("grant_type", "client_credentials"))
                .header(HEADER_CONTENT_TYPE, "application/x-www-form-urlencoded")
                .retrieve()
                .bodyToMono(AccessToken.class)
                .block();
    }

    @SneakyThrows
    private String createRequestToken() {
        String nonce = UUID.randomUUID().toString();
        JwtClaims claims = new JwtClaims();
        // NumericDate 1 second in the past to prevent "iat" claim being in the future when it arrives on the server (minor time difference)
        NumericDate iatNumericDate = NumericDate.now();
        iatNumericDate.addSeconds(-1);
        claims.setIssuer(clientId.toString());
        claims.setJwtId(nonce);
        claims.setIssuedAt(iatNumericDate);
        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setKey(signingKey);
        jws.setKeyIdHeaderValue(ytsVerificationKeyId);
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);
        return jws.getCompactSerialization();
    }
}
