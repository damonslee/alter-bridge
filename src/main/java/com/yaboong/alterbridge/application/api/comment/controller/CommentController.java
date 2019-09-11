package com.yaboong.alterbridge.application.api.comment.controller;

import com.yaboong.alterbridge.application.api.comment.domain.CommentDto;
import com.yaboong.alterbridge.application.common.response.ApiResponse;
import com.yaboong.alterbridge.application.common.response.ResponseBase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by yaboong on 2019-09-11
 */
@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    @PostMapping
    public ResponseEntity createComment(@RequestBody @Valid CommentDto commentDto, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body(ResponseBase.of(ApiResponse.INVALID_REQUEST));
        }

        return ResponseEntity
                .ok()
                .body(ResponseBase.of(ApiResponse.OK));
    }

}
