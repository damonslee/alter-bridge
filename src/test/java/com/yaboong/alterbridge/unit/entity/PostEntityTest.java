package com.yaboong.alterbridge.unit.entity;

import com.yaboong.alterbridge.TestProfile;
import com.yaboong.alterbridge.application.api.post.Post;
import com.yaboong.alterbridge.application.api.post.PostRepository;
import com.yaboong.alterbridge.configuration.querydsl.QuerydslConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by yaboong on 2019-08-29.
 */
@DataJpaTest
@RunWith(SpringRunner.class)
@Import(QuerydslConfiguration.class)
@ActiveProfiles(TestProfile.TEST)
public class PostEntityTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private PostRepository postRepository;

    @Test
    public void 게시글_등록() {
        // GIVEN
        Post post = Post.builder()
                .title("등록테스트입니다.")
                .category("개발")
                .content("내용입니다.")
                .createdBy("yaboong")
                .build();

        // WHEN
        testEntityManager.persist(post);

        Post savedPost = postRepository.getOne(post.getPostId());

        // THEN
        assertThat(savedPost, is(post));
        assertEquals(savedPost.getTitle(), "등록테스트입니다.");
    }

    @Test
    public void querydslTest() {
        // GIVEN
        Post post = Post.builder()
                .title("등록테스트입니다.")
                .category("개발")
                .content("내용입니다.")
                .createdBy("yaboong")
                .build();

        // WHEN
        testEntityManager.persist(post);
        Post savedPost = postRepository.findByTitle(post.getTitle()).get(0);

        // THEN
        assertThat(savedPost, is(post));
    }
}
