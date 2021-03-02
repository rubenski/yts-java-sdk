package com.yolt.yts.sdk.service;

public class ResponseException extends RuntimeException {

    public ResponseException(int status, String body) {
        super("Received HTTP error status " + status + " with body " + body);
    }
}
