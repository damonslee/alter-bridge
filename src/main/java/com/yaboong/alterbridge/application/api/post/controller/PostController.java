package com.yaboong.alterbridge.application.api.post.controller;

import com.yaboong.alterbridge.application.api.post.domain.PostDto;
import com.yaboong.alterbridge.application.api.post.entity.Post;
import com.yaboong.alterbridge.application.api.post.service.PostService;
import com.yaboong.alterbridge.application.common.response.ApiResponse;
import com.yaboong.alterbridge.application.common.response.ResponseBase;
import com.yaboong.alterbridge.application.common.validation.DtoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by yaboong on 2019-09-11
 */
@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

    private final DtoValidator dtoValidator;

    private final PostService postService;

    @GetMapping("/{id}")
    public ResponseEntity getPost(@PathVariable Long id) {
        return postService.get(id)
                .map(post -> ResponseEntity.ok(ResponseBase.of(ApiResponse.OK, post)))
                .orElseGet(() -> ResponseEntity.notFound().build())
                ;
    }


    @PostMapping
    public ResponseEntity createPost(
            @RequestBody @Valid PostDto postDto,
            Errors errors
    ) {
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

        Post newPost = postService.create(postDto);

        return ResponseEntity
                .created(linkTo(PostController.class).slash(newPost.getPostId()).toUri())
                .body(ResponseBase.of(ApiResponse.OK, newPost));
    }

    @PutMapping("/{id}")
    public ResponseEntity updatePost(
            @PathVariable Long id,
            @RequestBody @Valid PostDto postDto,
            Errors errors
    ) {
        if (errors.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body(ResponseBase.of(ApiResponse.INVALID_REQUEST, errors))
                    ;
        }

        dtoValidator.validate(postDto, errors);
        if (errors.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body(ResponseBase.of(ApiResponse.INVALID_REQUEST, errors))
                    ;
        }

        return postService
                .modify(id, postDto)
                .map(post -> ResponseEntity.ok(ResponseBase.of(ApiResponse.OK, post)))
                .orElseGet(() -> ResponseEntity.notFound().build())
                ;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity softDeletePost(@PathVariable Long id) {
        return postService
                .softRemove(id)
                .map(post -> ResponseEntity.ok(ResponseBase.of(ApiResponse.OK, post)))
                .orElseGet(() -> ResponseEntity.notFound().build())
                ;
    }

}
