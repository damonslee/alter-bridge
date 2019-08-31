package com.yaboong.alterbridge.unit.entity;

import com.yaboong.alterbridge.TestProfile;
import com.yaboong.alterbridge.application.api.comment.Comment;
import com.yaboong.alterbridge.application.api.comment.CommentRepository;
import com.yaboong.alterbridge.application.api.post.Post;
import com.yaboong.alterbridge.configuration.jpa.JpaConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by yaboong on 2019-08-31
 */
@DataJpaTest
@RunWith(SpringRunner.class)
@Import(JpaConfiguration.class)
@ActiveProfiles(TestProfile.TEST)
public class PostCommentMappingTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CommentRepository commentRepository;

    @Test
    public void 매핑테스트_Post_Comment() {
        // GIVEN
        Comment comment = Comment.builder()
                .content("TEST COMMENT CONTENT")
                .build();

        Post post = Post.builder()
                .title("TEST POST TITLE")
                .category("DEV")
                .content("TEST POST CONTENT")
                .build();
        post.add(comment);

        // WHEN
        testEntityManager.persist(post);

        // THEN
        Comment commentFound = commentRepository.findByContent("TEST COMMENT CONTENT").get(0);
        assertNotNull(commentFound);
        assertEquals(post.getPostId(), comment.getPost().getPostId());
        assertEquals(comment.getContent(), commentFound.getContent());
        assertEquals(post.getTitle(), commentFound.getPost().getTitle());
    }
}
