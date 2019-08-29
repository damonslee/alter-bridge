package com.yaboong.alterbridge.application.api.post;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yaboong on 2019-08-29.
 */
public interface PostRepository extends JpaRepository<Post, Long> {
}
