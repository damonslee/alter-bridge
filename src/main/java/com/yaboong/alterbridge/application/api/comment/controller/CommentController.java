package com.yaboong.alterbridge.application.api.comment.controller;

import com.yaboong.alterbridge.application.api.comment.Comment;
import com.yaboong.alterbridge.application.api.comment.domain.CommentDto;
import com.yaboong.alterbridge.application.api.comment.service.CommentService;
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
@RequestMapping(value = "/posts/{postId}/comments", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
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
                    .body(errors);
        }

        Comment newComment = commentService.create(postId, commentDto);

        return ResponseEntity
                .created(linkTo(CommentController.class, postId).slash(newComment.getCommentId()).toUri())
                .body(newComment);
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
                    .body(errors);
        }

        return commentService
                .modify(commentId, commentDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity softDeleteComment(
            @PathVariable Long commentId
    ) {
        return commentService
                .softRemove(commentId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
