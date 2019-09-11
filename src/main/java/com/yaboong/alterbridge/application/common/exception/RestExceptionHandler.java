package com.yaboong.alterbridge.application.common.exception;

import com.yaboong.alterbridge.application.common.response.ResponseBase;
import com.yaboong.alterbridge.application.common.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by yaboong on 2019-08-30
 */
@Slf4j
@ControllerAdvice
public class RestExceptionHandler  {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity handleException(Exception exception) {
        return responseError(
                ApiResponse.SOMETHING_WENT_WRONG,
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception
        );
    }

    @ExceptionHandler(ApiException.class)
    @ResponseBody
    public ResponseEntity handleApiException(ApiException apiException) {
        return responseError(
                ApiResponse.UNKNOWN_ERROR,
                HttpStatus.BAD_REQUEST,
                apiException
        );
    }

    private ResponseEntity responseError(ApiResponse apiResponse, HttpStatus status, Exception exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(status)
                .body(ResponseBase.of(apiResponse, exception.getClass().getSimpleName()));
    }
}
