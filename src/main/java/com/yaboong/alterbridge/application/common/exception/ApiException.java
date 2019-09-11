package com.yaboong.alterbridge.application.common.exception;

import com.yaboong.alterbridge.application.common.response.ApiResponse;
import lombok.NoArgsConstructor;

/**
 * Created by yaboong on 2019-08-30
 */
@NoArgsConstructor
public class ApiException extends RuntimeException {

    public ApiException(String message) {
        super(message);
    }

    public ApiException(ApiResponse apiResponse) {
        super(apiResponse.message());
    }

}
