package com.yolt.yts.sdk.http;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.client.reactive.ClientHttpRequest;
import org.springframework.http.client.reactive.ClientHttpRequestDecorator;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * Borrowed from https://github.com/spring-projects/spring-framework/issues/24262
 */
@Slf4j
class BodyLoggingRequestDecorator extends ClientHttpRequestDecorator {

    public BodyLoggingRequestDecorator(ClientHttpRequest request) {
        super(request);
    }

    @Override
    public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
        StringBuffer sb = new StringBuffer("");
        getDelegate().getHeaders().forEach((k, v) -> {
            sb.append(k).append(":").append(v.size() == 1 ? v.get(0) : v).append("\n");
        });
        body = DataBufferUtils.join(body)
                .doOnNext(content -> {
                    sb.append("Body: ").append(content.toString(StandardCharsets.UTF_8));
                    log.info("\n{} {}\n{}", getDelegate().getMethod(), getDelegate().getURI(), sb);
                });
        return super.writeWith(body);
    }
}