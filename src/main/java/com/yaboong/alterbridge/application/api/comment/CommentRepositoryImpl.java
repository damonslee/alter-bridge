package com.yaboong.alterbridge.application.api.comment;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.yaboong.alterbridge.application.api.comment.QComment.comment;

/**
 * Created by yaboong on 2019-08-29.
 */
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Comment> findByContent(String content) {
        return queryFactory.selectFrom(comment)
                .where(comment.content.eq(content))
                .fetch()
                ;
    }
}
