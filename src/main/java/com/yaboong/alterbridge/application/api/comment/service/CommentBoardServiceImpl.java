package com.yaboong.alterbridge.application.api.comment.service;

import com.yaboong.alterbridge.application.api.board.service.BoardService;
import com.yaboong.alterbridge.application.api.comment.domain.CommentDto;
import com.yaboong.alterbridge.application.api.comment.entity.Comment;
import com.yaboong.alterbridge.application.api.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by yaboong on 2019-09-12
 */
@Service
@RequiredArgsConstructor
public class CommentBoardServiceImpl implements BoardService<Comment, CommentDto> {

    private final CommentRepository commentRepository;

    private final ModelMapper modelMapper;

    @Override
    public Comment create(CommentDto commentDto) {
        return null;
    }

    @Override
    public Optional<Comment> modify(Long postId, CommentDto commentDto) {
        return Optional.empty();
    }

    @Override
    public Optional<Comment> softRemove(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Comment> get(Long id) {
        return Optional.empty();
    }

    @Override
    public void remove(Long id) {

    }
}
