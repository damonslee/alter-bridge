package com.yaboong.alterbridge.application.common.exception;

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
                HttpStatus.INTERNAL_SERVER_ERROR,
                exception
        );
    }

//    @ExceptionHandler(ApiException.class)
//    @ResponseBody
//    public ResponseEntity handleApiException(ApiException apiException) {
//        return responseError(
//                HttpStatus.BAD_REQUEST,
//                apiException
//        );
//    }

    private ResponseEntity responseError(HttpStatus status, Exception exception) {
        log.error(exception.getMessage(), exception);
        return ResponseEntity
                .status(status)
                .body(exception.getClass().getSimpleName());
    }
}
