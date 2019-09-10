package com.yaboong.alterbridge.application.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

/**
 * Created by yaboong on 2019-09-11
 */
public class ResponseEntityBuilder {
    public static ResponseEntity errors(HttpStatus httpStatus, ApiResponse apiResponse, Errors errors) {
        return ResponseEntity.status(httpStatus).body(ResponseBase.of(apiResponse, errors));
    }

    public static ResponseEntity badRequest(Errors errors) {
        return ResponseEntity.badRequest().body(ResponseBase.of(ApiResponse.INVALID_REQUEST, errors));
    }

    public static ResponseEntity ok(HttpStatus httpStatus, ApiResponse apiResponse) {
        return ResponseEntity.status(httpStatus).body(ResponseBase.of(apiResponse));
    }

    public static ResponseEntity ok() {
        return ResponseEntity.ok().body(ResponseBase.of(ApiResponse.OK));
    }
}
