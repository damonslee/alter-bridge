package com.yaboong.alterbridge.application.api.post.service;

import com.yaboong.alterbridge.application.api.post.domain.PostDto;
import com.yaboong.alterbridge.application.api.post.entity.Post;

import java.util.Optional;

/**
 * Created by yaboong on 2019-09-13
 */
public interface PostService {

    Post create(PostDto dto);

    Optional<Post> modify(Long id, PostDto dto);

    Optional<Post> softRemove(Long id);

    Optional<Post> get(Long id);

    void remove(Long id);

}
