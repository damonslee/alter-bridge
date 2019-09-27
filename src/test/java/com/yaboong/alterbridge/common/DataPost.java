package com.yaboong.alterbridge.common;

import com.yaboong.alterbridge.application.api.post.entity.Post;
import com.yaboong.alterbridge.application.common.type.Status;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yaboong on 2019-09-27
 */
public class DataPost implements Input {

    public static Post newPost(int idx) {
        return buildPost(idx);
    }

    public static Post saveNewPost(JpaRepository<Post, Long> postRepository, int idx) {
        Post post = buildPost(idx);
        return postRepository.save(post);
    }

    private static Post buildPost(int idx) {
        return Post.builder()
                .title(TITLE + idx)
                .content(CONTENT + idx)
                .category(Post.Category.GENERAL)
                .status(Status.NORMAL)
                .likeCount(0L)
                .viewCount(0L)
                .build();
    }
}
