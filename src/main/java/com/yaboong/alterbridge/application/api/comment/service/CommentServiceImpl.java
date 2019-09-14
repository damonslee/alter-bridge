package com.yaboong.alterbridge.application.api.comment.service;

import com.yaboong.alterbridge.application.api.comment.domain.CommentDto;
import com.yaboong.alterbridge.application.api.post.entity.Post;
import com.yaboong.alterbridge.application.api.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by yaboong on 2019-09-12
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final PostRepository postRepository;

    private final ModelMapper modelMapper;

    @Override
    public Post create(Long postId, CommentDto commentDto) {
        Post newComment = modelMapper.map(commentDto, Post.class);
        return postRepository
                .findById(postId)
                .map(parentPost -> {
                    newComment.setCategory(parentPost.getCategory());
                    newComment.setParent(parentPost);
                    return newComment;
                })
                .map(postRepository::save)
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public Optional<Post> modify(Long parentPostId, Long commentId, CommentDto commentDto) {
        return postRepository
                .findByParentPostIdAndPostId(parentPostId, commentId)
                .map(comment -> {
                    modelMapper.map(commentDto, comment);
                    return comment;
                })
                .map(postRepository::save);
    }

    @Override
    public Optional<Post> softRemove(Long parentPostId, Long commentId) {
        return postRepository
                .findByParentPostIdAndPostId(parentPostId, commentId)
                .map(comment -> {
                    comment.setStatus(Post.Status.DELETED);
                    return comment;
                })
                .map(postRepository::save);
    }
}
