package com.yaboong.alterbridge.application.api.post.repository;

import com.yaboong.alterbridge.application.api.post.entity.Post;
import com.yaboong.alterbridge.application.common.type.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


/**
 * Created by yaboong on 2019-08-29.
 */
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    Optional<Post> findByPostIdAndStatus(Long postId, Status status);

}
