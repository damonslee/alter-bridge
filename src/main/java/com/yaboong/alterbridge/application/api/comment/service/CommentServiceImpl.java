package com.yaboong.alterbridge.application.api.comment.service;

import com.yaboong.alterbridge.application.api.comment.entity.Comment;
import com.yaboong.alterbridge.application.api.comment.domain.CommentDto;
import com.yaboong.alterbridge.application.api.comment.repository.CommentRepository;
import com.yaboong.alterbridge.application.api.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
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

    @Override
    public Comment create(Long postId, CommentDto commentDto) {
        return postRepository
                .findById(postId)
                .map(post -> {
                    Comment newComment = new Comment();
                    newComment.addPost(post);
                    return newComment;
                })
                .map(commentRepository::save)
                .orElseThrow(IllegalArgumentException::new);
    }

    @Override
    public Optional<Comment> modify(Long commentId, CommentDto commentDto) {
        return commentRepository
                .findById(commentId)
                .map(comment -> comment.apply(commentDto));
    }

    @Override
    public Optional<Comment> softRemove(Long commentId) {
        return commentRepository
                .findById(commentId)
                .map(Comment::delete);
    }

    @Override
    public void remove(Long id) {
        commentRepository.deleteById(id);
    }
}
