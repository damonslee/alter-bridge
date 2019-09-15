package com.yaboong.alterbridge.common;

import com.yaboong.alterbridge.application.api.post.entity.Post;
import com.yaboong.alterbridge.application.api.post.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

/**
 * Created by yaboong on 2019-09-16
 */
@TestComponent
public class TestDataGenerator {

    @Autowired
    private PostRepository postRepository;

    public Post newPost(int idx) {
        Post post = Post.builder()
                .title("dummy post title #" + idx)
                .content("dummy post content #" + idx)
                .category(Post.Category.GENERAL)
                .status(Post.Status.NORMAL)
                .likeCount(0L)
                .viewCount(0L)
                .build();

        return this.postRepository.save(post);
    }

}
