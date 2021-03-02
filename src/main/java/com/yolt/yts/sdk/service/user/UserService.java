package com.yolt.yts.sdk.service.user;

import com.yolt.yts.sdk.service.accesstoken.AccessToken;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import static com.yolt.yts.sdk.Constants.*;

public class UserService {

    private final WebClient webClient;

    public UserService(WebClient webClient) {
        this.webClient = webClient;
    }

    /**
     * The request for creating a default user is a POST without a body
     *
     * @param token
     * @return
     */
    public User createUser(AccessToken token) {
        return webClient.post()
                .uri(PATH_USERS)
                .header(HEADER_CONTENT_TYPE, "application/json")
                .header(HEADER_AUTHORIZATION, "Bearer " + token.getAccessToken())
                .retrieve()
                .bodyToMono(User.class)
                .block();
    }

    public User createUser(AccessToken token, KycDetails kycDetails
    ) {
        return webClient.post()
                .uri(PATH_USERS)
                .header(HEADER_CONTENT_TYPE, "application/json")
                .header(HEADER_AUTHORIZATION, "Bearer " + token.getAccessToken())
                .body(BodyInserters.fromValue(kycDetails))
                .retrieve()
                .bodyToMono(User.class)
                .block();
    }

}
