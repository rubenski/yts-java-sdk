package com.yolt.yts.sdk.http;

import com.yolt.yts.sdk.PfxLoader;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.Request;
import org.eclipse.jetty.http.HttpField;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.springframework.http.client.reactive.JettyClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.*;


/**
 * Creates a Spring WebClient that uses Apache's CloseableHttpAsyncClient
 */
@Slf4j
public class WebClientCreator {

    public static WebClient createWithJetty(HttpClientConfig config) {

        SslContextFactory.Client sslContextFactory = new SslContextFactory.Client();
        sslContextFactory.setSslContext(createSslContext(config));
        HttpClient httpClient = new HttpClient(sslContextFactory) {
            @Override
            public Request newRequest(URI uri) {
                Request request = super.newRequest(uri);
                if (config.isLogRequestsResponses()) {
                    return enhanceWithLogging(request);
                }
                return request;
            }
        };

        httpClient.setConnectTimeout(config.getConnectTimeOutMs());
        httpClient.setStopTimeout(config.getSocketTimeOutMs());

        return WebClient.builder()
                .baseUrl(config.getBaseUrl())
                .clientConnector(new JettyClientHttpConnector(httpClient))
                .build();

    }

    private static Request enhanceWithLogging(Request inboundRequest) {
        StringBuilder msg = new StringBuilder();
        // Request Logging
        inboundRequest.onRequestBegin(request ->
                msg.append("\n\nREQUEST\n")
                        .append(request.getMethod()).append(" ").append(request.getURI())
                        .append("\n"));
        inboundRequest.onRequestHeaders(request -> {
            msg.append("Headers: \n");
            for (HttpField header : request.getHeaders()) {
                msg.append("\t" + header.getName() + " : " + header.getValue() + "\n");
            }
        });
        inboundRequest.onRequestContent((request, content) -> {
            msg.append("Body: \n\t").append(new String(content.array()));
        });


        // Response Logging
        inboundRequest.onResponseBegin(response ->
                msg.append("\n\nRESPONSE\n")
                        .append("Status: ")
                        .append(response.getStatus())
                        .append("\n"));
        inboundRequest.onResponseHeaders(response -> {
            msg.append("Headers:\n");
            for (HttpField header : response.getHeaders()) {
                msg.append("\t" + header.getName() + " : " + header.getValue() + "\n");
            }
        });

        msg.append("Body:\n\t");
        inboundRequest.onResponseContent(((response, content) -> {
            var bufferAsString = StandardCharsets.UTF_8.decode(content).toString();
            msg.append(bufferAsString);
        }));

        inboundRequest.onResponseSuccess(response -> log.info(msg.toString() + "\n\n"));

        return inboundRequest;
    }

    @SneakyThrows
    private static SSLContext createSslContext(HttpClientConfig cfg) {
        SSLContext ctx = SSLContext.getInstance("TLSv1.2");
        try {
            if (cfg.isMtls()) {
                KeyStore keyStore = PfxLoader.load(cfg.getKeyStoreClassPath(), cfg.getKeyStorePw());
                TrustManagerFactory fact = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
                keyManagerFactory.init(keyStore, cfg.getKeyStorePw().toCharArray());
                fact.init(keyStore);
                ctx.init(keyManagerFactory.getKeyManagers(), null, null);
            } else {
                // Creates a default SSL context which (I assume) trusts CAs from the OS's cacerts
                ctx.init(null, null, null);
            }
        } catch (NoSuchAlgorithmException | KeyStoreException | UnrecoverableKeyException | KeyManagementException e) {
            throw new RuntimeException(e);
        }
        return ctx;
    }


}
