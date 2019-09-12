package com.yaboong.alterbridge.application.api.comment.controller;

import com.yaboong.alterbridge.application.api.comment.domain.CommentDto;
import com.yaboong.alterbridge.application.api.comment.entity.Comment;
import com.yaboong.alterbridge.application.api.comment.service.CommentService;
import com.yaboong.alterbridge.application.common.response.ApiResponse;
import com.yaboong.alterbridge.application.common.response.ResponseBase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by yaboong on 2019-09-11
 */
@RestController
@RequestMapping("/posts/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity createComment(
            @PathVariable Long postId,
            @RequestBody @Valid CommentDto commentDto,
            Errors errors
    ) {
        if (errors.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body(ResponseBase.of(ApiResponse.INVALID_REQUEST, errors));
        }

        Comment newComment = commentService.create(postId, commentDto);
        return ResponseEntity
                .ok()
                .body(ResponseBase.of(ApiResponse.OK, newComment));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity updateComment(
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
                .modify(commentId, commentDto)
                .map(comment -> ResponseEntity.ok(ResponseBase.of(ApiResponse.OK, comment)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity softDeleteComment(
            @PathVariable Long commentId
    ) {
        return commentService
                .softRemove(commentId)
                .map(comment -> ResponseEntity.ok(ResponseBase.of(ApiResponse.OK, comment)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
