package com.yaboong.alterbridge.application.common.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;

/**
 * Created by yaboong on 2019-09-11
 *
 * ResponseEntity 생성하는 중복코드가 너무 많이 생겨서 만들긴 했으나,
 * ResponseEntity 클래스 자체가 체이닝 할 수 있는 메서드들이 너무 많고 케이스가 다양하기 때문에,
 * 이걸 다 커버하는 유틸클래스를 만들려면 결국 ResponseEntity 보다 더 장황한 내용을 가지는 클래스가 생길 것 같아서,
 * 안쓰고, ResponseEntity 그대로 사용하기로 결정함.
 */
@Deprecated
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
