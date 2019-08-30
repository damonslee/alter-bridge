package com.yaboong.alterbridge.application.common.response;

/**
 * Created by yaboong on 2019-08-31
 */
public enum ApiResponse {
    SUCCESS(0, "OK"),

    SOMETHING_WENT_WRONG(2001, "Something went wrong"),
    UNKNOWN_ERROR(2002, "Unknown error occurred"),
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
