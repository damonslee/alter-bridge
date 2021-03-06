package com.yaboong.alterbridge.application.common.response;

/**
 * Created by yaboong on 2019-08-31
 */
@Deprecated(since = "HATEAOS, Rest Docs applied")
public enum ApiResponse {
    OK(0, "OK"),

    SOMETHING_WENT_WRONG(2001, "Something went wrong"),
    UNKNOWN_ERROR(2002, "Unknown error occurred"),
    INVALID_REQUEST(3001, "Invalid request"),
    POST_NOT_EXISTS(3002, "Post not exists")
    ;

    private int code;
    private String message;

    ApiResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }
}
