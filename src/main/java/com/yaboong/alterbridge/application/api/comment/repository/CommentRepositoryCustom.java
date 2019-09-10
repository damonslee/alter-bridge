package com.yaboong.alterbridge.application.api.comment.repository;

import com.yaboong.alterbridge.application.api.comment.entity.Comment;

import java.util.List;

/**
 * Created by yaboong on 2019-08-29.
 */
public interface CommentRepositoryCustom {
    List<Comment> findByContent(String content);
}
