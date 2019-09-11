package com.yaboong.alterbridge.application.api.post.service;

import com.yaboong.alterbridge.application.api.post.domain.PostDto;
import com.yaboong.alterbridge.application.api.post.entity.Post;

import java.util.Optional;

/**
 * Created by yaboong on 2019-09-11
 */
public interface PostService {

    Long create(PostDto postDto);

    Long modify(PostDto postDto);

    Optional<Post> get(Long postId);

    void remove(Long postId);

}
