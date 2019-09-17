package com.yaboong.alterbridge.application.api.post.repository;


import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yaboong.alterbridge.application.api.post.entity.Post;
import com.yaboong.alterbridge.application.common.type.Status;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

import static com.yaboong.alterbridge.application.api.comment.entity.QComment.comment;
import static com.yaboong.alterbridge.application.api.post.entity.QPost.post;

/**
 * Created by yaboong on 2019-08-29.
 */
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public Optional<Post> findPostAndCommentByPostId(Long postId, Status status) {
        Post postResult = queryFactory
                .selectFrom(post)
                .join(post.comments, comment)
                .fetchJoin() // fetchJoin() 없으면 join 쿼리하나, comment 쿼리하나 총 두개가 나감
                .where(post.postId.eq(postId)
                        .and(post.status.eq(status))
                )
                .fetchOne();
        return Optional.ofNullable(postResult);
    }

    public List<Post> findAllPost(Status status) {
        return queryFactory
                .selectFrom(post)
                .where(post.status.eq(status))
                .fetch();
    }

}
