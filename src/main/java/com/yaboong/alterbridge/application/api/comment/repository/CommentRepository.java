package com.yaboong.alterbridge.application.api.comment.repository;

import com.yaboong.alterbridge.application.api.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yaboong on 2019-09-16
 */
public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom {
}
