package com.yaboong.alterbridge.application.api.post.controller;

import com.yaboong.alterbridge.application.api.post.domain.PostDto;
import com.yaboong.alterbridge.application.common.validation.DtoValidator;
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
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final DtoValidator dtoValidator;

    @PostMapping
    public ResponseEntity createPost(@RequestBody @Valid PostDto postDto, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body(ResponseBase.of(ApiResponse.INVALID_REQUEST, errors));
            // return ResponseEntityBuilder.badRequest(errors);
        }

        dtoValidator.validate(postDto, errors);
        if (errors.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body(ResponseBase.of(ApiResponse.INVALID_REQUEST, errors));
        }

        return ResponseEntity
                .ok()
                .body(ResponseBase.of(ApiResponse.OK));
    }

}
