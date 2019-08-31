package com.yaboong.alterbridge.unit.utility;

import com.yaboong.alterbridge.TestProfile;
import com.yaboong.alterbridge.application.api.comment.Comment;
import com.yaboong.alterbridge.application.api.comment.CommentRepository;
import com.yaboong.alterbridge.application.api.post.Post;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by yaboong on 2019-08-31
 */
@DataJpaTest
@RunWith(SpringRunner.class)
@Import({QuerydslConfiguration.class, JpaConfiguration.class})
@ActiveProfiles(TestProfile.TEST)
public class QuerydslTest {

    @Autowired
    TestEntityManager testEntityManager;

    @Autowired
    CommentRepository commentRepository;

    @Test
    public void querydsl_동작확인() {
        // GIVEN
        Post post = Post.builder()
                .title("TEST POST TITLE")
                .category("DEV")
                .content("TEST POST CONTENT")
                .build();
        testEntityManager.persist(post);

        // WHEN
        Comment comment = Comment.builder()
                .post(post)
                .content("TEST COMMENT CONTENT")
                .build();
        testEntityManager.persist(comment);

        // THEN
        Comment commentFound = commentRepository.findByContent("TEST COMMENT CONTENT").get(0);
        assertNotNull(commentFound);
        assertEquals(comment.getContent(), commentFound.getContent());
        assertEquals(post.getTitle(), commentFound.getPost().getTitle());
    }
}
