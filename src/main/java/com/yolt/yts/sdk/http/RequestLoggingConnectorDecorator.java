package com.yolt.yts.sdk.http;

import org.springframework.http.HttpMethod;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.http.client.reactive.ClientHttpResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.function.Function;

/**
 * Borrowed from https://github.com/spring-projects/spring-framework/issues/24262
 * <p>
 * Request body logging is only possible at low level, by overriding the connect method that provides access to the
 * DataBuffer in the ClientRequest
 */

public class RequestLoggingConnectorDecorator implements ClientHttpConnector {

    private final ClientHttpConnector delegate;

    public RequestLoggingConnectorDecorator(ClientHttpConnector delegate) {
        this.delegate = delegate;
    }

    @Override
    public Mono<ClientHttpResponse> connect(HttpMethod method, URI uri,
                                            Function<? super ClientHttpRequest, Mono<Void>> callback) {
        return this.delegate.connect(method, uri,
                request -> callback.apply(new BodyLoggingRequestDecorator(request)));
    }


}