package com.yaboong.alterbridge.application.common.exception;

/**
 * Created by yaboong on 2019-08-30
 */
public class ApiException extends RuntimeException {
    public ApiException() {
    }

    public ApiException(String message) {
        super(message);
    }
}
