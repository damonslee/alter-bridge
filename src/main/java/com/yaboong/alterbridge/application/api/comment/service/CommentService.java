package com.yaboong.alterbridge.application.api.comment.service;

import com.yaboong.alterbridge.application.api.comment.domain.CommentDto;
import com.yaboong.alterbridge.application.api.comment.entity.Comment;

import java.util.Optional;

/**
 * Created by yaboong on 2019-09-13
 */
public interface CommentService {

    Comment create(Long postId, CommentDto dto);

    Optional<Comment> modify(Long id, CommentDto dto);

    Optional<Comment> softRemove(Long id);

    Optional<Comment> get(Long id);

    void remove(Long id);

}
