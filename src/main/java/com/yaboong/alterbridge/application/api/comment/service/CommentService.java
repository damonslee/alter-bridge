package com.yaboong.alterbridge.application.api.comment.service;

import com.yaboong.alterbridge.application.api.comment.domain.CommentDto;
import com.yaboong.alterbridge.application.api.post.entity.Post;

import java.util.Optional;

/**
 * Created by yaboong on 2019-09-13
 */
public interface CommentService {

    Post create(Long parentPostId, CommentDto dto);

    Optional<Post> modify(Long parentPostId, Long commentId, CommentDto dto);

    Optional<Post> softRemove(Long parentPostId, Long commentId);

}
