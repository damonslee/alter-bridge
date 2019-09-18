package com.yaboong.alterbridge.application.api.post.repository;


import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yaboong.alterbridge.application.api.post.entity.Post;
import com.yaboong.alterbridge.application.common.type.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

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
        Post postQueryResult =
                queryFactory
                        .selectFrom(post)
                        .join(post.comments, comment)
                        .fetchJoin() // fetchJoin() 없으면 join 쿼리하나, comment 쿼리하나 총 두개가 나감
                        .where(post.postId.eq(postId)
                                .and(post.status.eq(status))
                        )
                        .fetchOne();

        return Optional.ofNullable(postQueryResult);
    }

    public Page<Post> findAllPostPaging(Pageable pageable, Status status) {
        QueryResults<Post> postQueryResults =
                queryFactory
                        .selectFrom(post)
                        .where(post.status.eq(status))
                        .limit(pageable.getPageSize())
                        .offset(pageable.getOffset())
                        .fetchResults();

        return new PageImpl<>(
                postQueryResults.getResults(),
                pageable,
                postQueryResults.getTotal()
        );
    }

}
