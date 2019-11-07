package com.yaboong.alterbridge.application.api.post.service;

import com.yaboong.alterbridge.application.api.post.domain.PostDto;
import com.yaboong.alterbridge.application.api.post.entity.Post;
import com.yaboong.alterbridge.application.api.post.repository.PostRepository;
import com.yaboong.alterbridge.application.common.type.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by yaboong on 2019-09-11
 */
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public Page<Post> getList(Pageable pageable) {
        return postRepository.findAllPostPaging(pageable, Status.NORMAL);
    }

    @Override
    public Post create(PostDto postDto) {
        return postRepository.save(postDto.toEntity());
    }

    @Override
    public Optional<Post> modify(Long postId, PostDto postDto) {
        return postRepository
                .findById(postId)
                .map(post -> post.apply(postDto));
    }

    @Override
    public Optional<Post> softRemove(Long postId) {
        return get(postId)
                .map(Post::delete);
    }

    @Override
    public Optional<Post> get(Long postId) {
        return postRepository.findPostAndCommentByPostId(postId, Status.NORMAL);
    }

}
