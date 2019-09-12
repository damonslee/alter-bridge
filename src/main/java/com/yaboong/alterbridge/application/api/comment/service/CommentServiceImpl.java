package com.yaboong.alterbridge.application.api.comment.service;

import com.yaboong.alterbridge.application.api.comment.domain.CommentDto;
import com.yaboong.alterbridge.application.api.comment.entity.Comment;
import com.yaboong.alterbridge.application.api.comment.repository.CommentRepository;
import com.yaboong.alterbridge.application.api.post.repository.PostRepository;
import com.yaboong.alterbridge.application.common.exception.ApiException;
import com.yaboong.alterbridge.application.common.response.ApiResponse;
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

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    private final ModelMapper modelMapper;

    @Override
    public Comment create(Long postId, CommentDto commentDto) {
        Comment newComment = modelMapper.map(commentDto, Comment.class);
        return postRepository
                .findById(postId)
                .map(post -> {
                    newComment.setPost(post);
                    return commentRepository.save(newComment);
                })
                .orElseThrow(() -> new ApiException(ApiResponse.POST_NOT_EXISTS));
    }

    @Override
    public Optional<Comment> modify(Long id, CommentDto commentDto) {
        return Optional.empty();
    }

    @Override
    public Optional<Comment> softRemove(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Comment> get(Long commentId) {
        return commentRepository.findById(commentId);
    }

    @Override
    public void remove(Long id) {

    }
}
