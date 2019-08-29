package com.yaboong.alterbridge.application.api.comment;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yaboong on 2019-08-29.
 */
public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {
}
