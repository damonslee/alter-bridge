package com.yaboong.alterbridge.application.api.post.repository;

import com.yaboong.alterbridge.application.api.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Created by yaboong on 2019-08-29.
 */
public interface PostRepository extends JpaRepository<Post, Long>, PostRepositoryCustom {

    Optional<Post> findByPostIdAndDeletedYn(Long postId, String deletedYn);

    default Optional<Post> findByPostIdAndDeletedYn(Long postId) {
        return findByPostIdAndDeletedYn(postId, "N");
    }

}
