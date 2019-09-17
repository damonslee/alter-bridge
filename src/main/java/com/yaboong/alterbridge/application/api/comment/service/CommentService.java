package com.yaboong.alterbridge.application.api.comment.service;

import com.yaboong.alterbridge.application.api.comment.entity.Comment;
import com.yaboong.alterbridge.application.api.comment.domain.CommentDto;

import java.util.Optional;

/**
 * Created by yaboong on 2019-09-13
 */
public interface CommentService {

    Comment create(Long postId, CommentDto dto);

    Optional<Comment> modify(Long commentId, CommentDto dto);

    Optional<Comment> softRemove(Long commentId);

    void remove(Long id);

}
