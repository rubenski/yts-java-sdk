package com.yolt.yts.sdk.service.site;

import com.yolt.yts.sdk.Constants;
import com.yolt.yts.sdk.service.accesstoken.AccessToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

import static com.yolt.yts.sdk.Constants.*;

@Slf4j
public class SiteService {

    private final WebClient webClient;

    public SiteService(WebClient webClient) {
        this.webClient = webClient;
    }

    private URI buildUri(UriBuilder uriBuilder, UUID redirectUrlId, List<String> tags) {
        uriBuilder.path(Constants.PATH_SITES);

        if (redirectUrlId != null) {
            uriBuilder.queryParam("redirectUrlId", redirectUrlId.toString());
        }

        if (tags != null) {
            tags.forEach(tag -> uriBuilder.queryParam("tag", tag));
        }

        return uriBuilder.build();
    }

    public List<Site> getSites(AccessToken token, List<String> tags, UUID redirectUrlId) {
        return webClient.get()
                .uri(uriBuilder -> buildUri(uriBuilder, redirectUrlId, tags))
                .header(HEADER_CONTENT_TYPE, "application/json")
                .header(HEADER_AUTHORIZATION, "Bearer " + token.getAccessToken())
                .retrieve()
                .bodyToFlux(Site.class)
                .collectList()
                .block();
    }

    public byte[] getSiteLogo(AccessToken token, String path) {
        return webClient.get()
                .uri(path)
                .header(HEADER_AUTHORIZATION, "Bearer " + token.getAccessToken())
                .retrieve()
                .bodyToMono(ByteArrayResource.class)
                .block()
                .getByteArray();
    }

    public Site getSite(AccessToken token, UUID siteId) {
        return webClient.get()
                .uri(PATH_SITES + "/" + siteId.toString())
                .header(HEADER_CONTENT_TYPE, "application/json")
                .header(HEADER_AUTHORIZATION, "Bearer " + token.getAccessToken())
                .retrieve()
                .bodyToMono(Site.class)
                .block();
    }


}
