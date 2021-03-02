package com.yolt.yts.sdk;

import lombok.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;

public class PfxLoader {

    public static KeyStore load(@NonNull String pfxPath, String pfxPassword) {
        KeyStore ks = null;
        try (InputStream keyFile = PfxLoader.class.getResourceAsStream(pfxPath)) {
            if (keyFile == null) {
                throw new RuntimeException("Could not find " + pfxPath);
            }
            ks = KeyStore.getInstance("PKCS12", "SunJSSE");
            ks.load(keyFile, pfxPassword.toCharArray());
            return ks;
        } catch (KeyStoreException | NoSuchProviderException | IOException | NoSuchAlgorithmException | CertificateException e) {
            throw new RuntimeException(e);
        }
    }
}
