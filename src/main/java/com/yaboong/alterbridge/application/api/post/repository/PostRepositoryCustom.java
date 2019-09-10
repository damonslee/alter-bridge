package com.yaboong.alterbridge.application.api.post.repository;

import com.yaboong.alterbridge.application.api.post.entity.Post;

import java.util.List;

/**
 * Created by yaboong on 2019-08-29.
 */
public interface PostRepositoryCustom {
    List<Post> findByTitle(String title);
}
