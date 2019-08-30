package com.yaboong.alterbridge.application.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * Created by yaboong on 2019-08-31
 */
@Data
@AllArgsConstructor(staticName = "of")
public class ResponseBase<T> {
    private int code;
    private String message;
    private T properties;

    public static ResponseBase of(ApiResponse apiResponse) {
        return ResponseBase.of(apiResponse.code(), apiResponse.message(), StringUtils.EMPTY);
    }

    public static <E> ResponseBase<E> of(ApiResponse apiResponse, E a) {
        return ResponseBase.of(apiResponse.code(), apiResponse.message(), a);
    }
}
