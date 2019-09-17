package com.yaboong.alterbridge.application.api.post.repository;

import com.yaboong.alterbridge.application.api.post.entity.Post;
import com.yaboong.alterbridge.application.common.type.Status;

import java.util.List;
import java.util.Optional;

/**
 * Created by yaboong on 2019-08-29.
 */
public interface PostRepositoryCustom {
    Optional<Post> findPostAndCommentByPostId(Long postId, Status status);

    List<Post> findAllPost(Status status);

}
