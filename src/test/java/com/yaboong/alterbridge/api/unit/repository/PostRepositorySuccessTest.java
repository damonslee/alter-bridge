package com.yaboong.alterbridge.api.unit.repository;

import com.yaboong.alterbridge.application.api.post.entity.Post;
import com.yaboong.alterbridge.application.api.post.repository.PostRepository;
import com.yaboong.alterbridge.application.common.type.Status;
import com.yaboong.alterbridge.common.TestDescription;
import com.yaboong.alterbridge.common.TestProfile;
import com.yaboong.alterbridge.configuration.jpa.JpaConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;
import java.util.function.Consumer;

import static org.junit.Assert.*;

/**
 * Created by yaboong on 2019-09-20
 */
@DataJpaTest
@RunWith(SpringRunner.class)
@Import(JpaConfiguration.class)
@ActiveProfiles(TestProfile.TEST)
public class PostRepositorySuccessTest {

    @Autowired
    PostRepository postRepository;

    Status status = Status.NORMAL;

    @Test
    @TestDescription("postId 로 게시물 한개 조회하기 테스트")
    public void PostRepository_findPostAndCommentByPostId() {
        // GIVEN
        Long postId = 1L;

        // WHEN
        Optional<Post> postOptional = postRepository.findPostAndCommentByPostId(postId, status);

        // THEN
        assertTrue(postOptional.isPresent());
        assertNotNull(postOptional.get());
        postOptional.ifPresent(validatePostField);
    }

    private Consumer<Post> validatePostField = post -> {
        assertNotNull(post.getPostId());
        assertEquals(post.getStatus(), status);
        assertNotNull(post.getTitle());
        assertNotNull(post.getCategory());
        assertNotNull(post.getContent());
        assertNotNull(post.getViewCount());
        assertNotNull(post.getLikeCount());
        assertNotNull(post.getCreatedAt());
        assertNotNull(post.getCreatedBy());
        assertNotNull(post.getModifiedAt());
        assertNotNull(post.getModifiedBy());
    };
}
