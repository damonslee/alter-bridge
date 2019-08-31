package com.yaboong.alterbridge.unit.utility;

import com.yaboong.alterbridge.TestProfile;
import com.yaboong.alterbridge.application.api.post.Post;
import com.yaboong.alterbridge.application.api.post.PostRepository;
import com.yaboong.alterbridge.configuration.jpa.JpaConfiguration;
import com.yaboong.alterbridge.configuration.querydsl.QuerydslConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by yaboong on 2019-08-31
 */
@DataJpaTest
@RunWith(SpringRunner.class)
@Import(value = {QuerydslConfiguration.class, JpaConfiguration.class})
@ActiveProfiles(TestProfile.TEST)
public class JpaAuditingTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private PostRepository postRepository;

    @Test
    public void jpaAuditing_동작확인() {
        // GIVEN
        Post post = Post.builder()
                .title("Old Post")
                .category("DEV")
                .content("Old Content")
                .build();

        // WHEN
        postRepository.saveAndFlush(post);

        // THEN
        assertEquals(post.getTitle(), "Old Post");
        assertEquals(post.getCreatedBy(), "admin");
        assertThat(post.getCreatedAt(), is(notNullValue()));
        assertEquals(post.getModifiedBy(), "admin");
        assertThat(post.getModifiedAt(), is(notNullValue()));
        LocalDateTime oldModifiedAt = post.getModifiedAt();

        // GIVEN
        Post modifiedPost = postRepository.getOne(post.getPostId());

        // WHEN
        modifiedPost.setTitle("New Post");
        postRepository.saveAndFlush(modifiedPost);
        testEntityManager.refresh(modifiedPost); // refresh 해주지 않으면 값이 업데이트가 안됨. 계속 캐싱하고 있는 값으로 사용함.

        // THEN
        assertEquals(modifiedPost.getTitle(), "New Post");
        assertThat(modifiedPost.getModifiedAt(), greaterThan(oldModifiedAt));
    }
}
