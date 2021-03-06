package com.yaboong.alterbridge.api.unit.repository;

import com.yaboong.alterbridge.application.api.post.domain.PostDto;
import com.yaboong.alterbridge.application.api.post.entity.Post;
import com.yaboong.alterbridge.application.api.post.repository.PostRepository;
import com.yaboong.alterbridge.common.DataPostDto;
import com.yaboong.alterbridge.common.TestProfile;
import com.yaboong.alterbridge.configuration.jpa.JpaConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityTransaction;
import java.time.LocalDateTime;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by yaboong on 2019-08-31
 */
@DataJpaTest
@RunWith(SpringRunner.class)
@Import(value = {JpaConfiguration.class})
@ActiveProfiles(TestProfile.TEST)
public class JpaAuditingTest {

    @Autowired
    private TestEntityManager em;

    @Autowired
    private PostRepository postRepository;

    @Test
    public void JpaAuditing_PostRepository_동작확인() {
        // GIVEN
        Post post = Post.builder()
                .title("Old Post")
                .category(Post.Category.GENERAL)
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
        PostDto newPostDto = DataPostDto.newPostDto();

        // WHEN
        modifiedPost.apply(newPostDto);
        postRepository.saveAndFlush(modifiedPost);

        // THEN
        assertEquals(modifiedPost.getTitle(), newPostDto.getTitle());
        assertThat(modifiedPost.getModifiedAt(), greaterThan(oldModifiedAt));
    }

    @Test
    public void JpaAuditing_EntityManager_동작확인() {
        // GIVEN
        Post post = Post.builder()
                .title("Old Post")
                .category(Post.Category.GENERAL)
                .content("Old Content")
                .build();

        // WHEN
        em.persist(post);
        EntityTransaction transaction1 = em.getEntityManager().getTransaction();
        transaction1.commit();

        // THEN
        assertEquals(post.getTitle(), "Old Post");
        assertEquals(post.getCreatedBy(), "admin");
        assertThat(post.getCreatedAt(), is(notNullValue()));
        assertEquals(post.getModifiedBy(), "admin");
        assertThat(post.getModifiedAt(), is(notNullValue()));

        // GIVEN
        EntityTransaction transaction2 = em.getEntityManager().getTransaction();
        transaction2.begin();
        LocalDateTime oldModifiedAt = post.getModifiedAt();
        PostDto newPostDto = DataPostDto.newPostDto();

        // WHEN
        post.apply(newPostDto);
        em.persist(post);
        transaction2.commit();

        // THEN
        assertEquals(post.getTitle(), newPostDto.getTitle());
        assertThat(post.getModifiedAt(), greaterThan(oldModifiedAt));
    }
}
