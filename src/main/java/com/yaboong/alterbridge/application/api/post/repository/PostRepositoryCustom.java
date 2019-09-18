package com.yaboong.alterbridge.application.api.post.repository;

import com.yaboong.alterbridge.application.api.post.entity.Post;
import com.yaboong.alterbridge.application.common.type.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Created by yaboong on 2019-08-29.
 */
public interface PostRepositoryCustom {
    Optional<Post> findPostAndCommentByPostId(Long postId, Status status);

    Page<Post> findAllPostPaging(Pageable pageable, Status status);

}
