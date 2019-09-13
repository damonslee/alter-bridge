package com.yaboong.alterbridge.application.api.comment.controller;

import com.yaboong.alterbridge.application.api.comment.domain.CommentDto;
import com.yaboong.alterbridge.application.api.comment.service.CommentService;
import com.yaboong.alterbridge.application.api.post.entity.Post;
import com.yaboong.alterbridge.application.common.response.ApiResponse;
import com.yaboong.alterbridge.application.common.response.ResponseBase;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * Created by yaboong on 2019-09-11
 */
@RestController
@RequestMapping(value = "/posts/{parentPostId}/comments", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity createComment(
            @PathVariable Long parentPostId,
            @RequestBody @Valid CommentDto commentDto,
            Errors errors
    ) {
        if (errors.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body(ResponseBase.of(ApiResponse.INVALID_REQUEST, errors));
        }

        Post newComment = commentService.create(parentPostId, commentDto);
        return ResponseEntity
                .created(linkTo(CommentController.class).slash(newComment.getPostId()).toUri())
                .body(ResponseBase.of(ApiResponse.OK, newComment));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity updateComment(
            @PathVariable Long parentPostId,
            @PathVariable Long commentId,
            @RequestBody @Valid CommentDto commentDto,
            Errors errors
    ) {
        if (errors.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body(ResponseBase.of(ApiResponse.INVALID_REQUEST, errors));
        }

        return commentService
                .modify(parentPostId, commentId, commentDto)
                .map(comment -> ResponseEntity.ok(ResponseBase.of(ApiResponse.OK, comment)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity softDeleteComment(
            @PathVariable Long parentPostId,
            @PathVariable Long commentId
    ) {
        return commentService
                .softRemove(parentPostId, commentId)
                .map(comment -> ResponseEntity.ok(ResponseBase.of(ApiResponse.OK, comment)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
