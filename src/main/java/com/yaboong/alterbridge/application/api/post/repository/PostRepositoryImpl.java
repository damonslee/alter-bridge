package com.yaboong.alterbridge.application.api.post.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yaboong.alterbridge.application.api.post.entity.Post;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.yaboong.alterbridge.application.api.post.entity.QPost.post;

/**
 * Created by yaboong on 2019-08-29.
 */
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Post> findByTitle(String title) {
        return queryFactory
                .selectFrom(post)
                .where(post.title.eq(title))
                .fetch()
                ;
    }
}
