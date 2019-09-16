package com.yaboong.alterbridge.common;

import com.yaboong.alterbridge.application.api.post.domain.PostDto;
import com.yaboong.alterbridge.application.api.post.entity.Post;
import com.yaboong.alterbridge.application.common.type.Status;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yaboong on 2019-09-16
 */
public class TestDataGenerator {
    private static final String TITLE = "dummy post title";
    private static final String CONTENT= "dummy post content";
    private static final String CATEGORY = "GENERAL";
    private static final String NORMAL = "NORMAL";

    public static Post newPost(JpaRepository<Post, Long> postRepository, int idx) {
        Post post = Post.builder()
                .title(TITLE + idx)
                .content(CONTENT + idx)
                .category(Post.Category.GENERAL)
                .status(Status.NORMAL)
                .likeCount(0L)
                .viewCount(0L)
                .build();
        return postRepository.save(post);
    }

    public static PostDto newPostDto() {
        return PostDto.builder()
                .category(CATEGORY)
                .title(TITLE)
                .content(CONTENT)
                .likeCount(0L)
                .viewCount(0L)
                .status(NORMAL)
                .build();
    }

}
