package com.yolt.yts.sdk.service;

public class YtsApiResponseException extends RuntimeException {
    int status;
    ErrorResponse errorResponse;

    public YtsApiResponseException(int status, ErrorResponse errorResponse) {
        super("HTTP status : " + status + " | YTS code : " + errorResponse.code + " | YTS message : " + errorResponse.message);
        this.status = status;
        this.errorResponse = errorResponse;
    }
}
