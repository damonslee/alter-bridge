package com.yaboong.alterbridge.application.api.post.service;

import com.yaboong.alterbridge.application.api.post.domain.PostDto;
import com.yaboong.alterbridge.application.api.post.entity.Post;
import com.yaboong.alterbridge.application.api.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by yaboong on 2019-09-11
 */
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final ModelMapper modelMapper;

    private final PostRepository postRepository;

    @Override
    public Post create(PostDto postDto) {
        Post post = modelMapper.map(postDto, Post.class);
        return postRepository.save(post);
    }

    @Override
    public Optional<Post> modify(Long postId, PostDto postDto) {
        return postRepository
                .findById(postId)
                .map(post -> {
                    modelMapper.map(postDto, post);
                    return post;
                })
                .map(postRepository::save);
    }

    @Override
    public Optional<Post> softRemove(Long postId) {
        return get(postId)
                .map(post -> {
                    post.setStatus(Post.Status.DELETED);
                    return post;
                })
                .map(postRepository::save);
    }

    @Override
    public Optional<Post> get(Long postId) {
        return postRepository.findByPostIdAndStatus(postId, Post.Status.NORMAL);
    }

}
