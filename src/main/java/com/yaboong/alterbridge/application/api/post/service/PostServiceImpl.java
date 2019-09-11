package com.yaboong.alterbridge.application.api.post.service;

import com.yaboong.alterbridge.application.api.post.domain.PostDto;
import com.yaboong.alterbridge.application.api.post.entity.Post;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by yaboong on 2019-09-11
 */
@Service
public class PostServiceImpl implements PostService {

    @Override
    public Long create(PostDto postDto) {
        return null;
    }

    @Override
    public Long modify(PostDto postDto) {
        return null;
    }

    @Override
    public Optional<Post> get(Long postId) {
        return Optional.empty();
    }

    @Override
    public void remove(Long postId) {

    }
}
