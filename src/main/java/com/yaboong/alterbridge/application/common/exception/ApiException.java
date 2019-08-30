package com.yaboong.alterbridge.application.common.exception;

import lombok.NoArgsConstructor;

/**
 * Created by yaboong on 2019-08-30
 */
@NoArgsConstructor
public class ApiException extends Exception {

    public ApiException(String message) {
        super(message);
    }

}
